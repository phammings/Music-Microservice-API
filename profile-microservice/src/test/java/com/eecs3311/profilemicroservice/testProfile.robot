# Don't forget!!
# Run nuke-songs-db.sh
# and also run MATCH (n) DETACH DELETE (n) in Neo4j
# before you run this Robot Framework script

*** Settings ***
Library           Collections
Library           RequestsLibrary
Test Timeout      30 seconds

Suite Setup    Run Keyword    All My Setup

*** Variables ***
${turpentine}
${turpentineTitle}
${ditto}
${dittoTitle}
${addicted}
${addictedTitle}
${ihonestlymightjustgiveup}
${ihonestlymightjustgiveupTitle}
${dummySongIdDeleted}
${testPerson1}
${testPerson2}
${testPerson3}


*** Keywords ***
All My Setup
    Run Keyword    Session Instantiation
    Run Keyword    Setup Song DB
    Run Keyword    Setup UserNames

Session Instantiation
    Create Session    songs    http://localhost:3001
    Create Session    profiles    http://localhost:3002

Setup Song DB
    # all of this should work, assuming testSongs.robot was already run and everything passed.
    ${songName}=    Set Variable    TURPENTINE
    Set Suite Variable    ${turpentineTitle}    ${songName}
    ${songArtistFullName}=    Set Variable    blink-182
    ${songAlbum}=    Set Variable    ONE MORE TIME...
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    songName=${songName}   songArtistFullName=${songArtistFullName}    songAlbum=${songAlbum}
    ${resp}=    POST On Session    songs    /addSong    json=${params}    headers=${headers}    expected_status=200
    ${data}=    Get From Dictionary    ${resp.json()}    data
    Dictionary Should Contain Key    ${data}    id
    ${turpentineId}=    Get From Dictionary    ${data}    id
    Set Suite Variable    ${turpentine}    ${turpentineId}
    Log To Console    Song titled ${songName} has ID ${turpentine}

    ${songName}=    Set Variable    Ditto
    Set Suite Variable    ${dittoTitle}    ${songName}
    ${songArtistFullName}=    Set Variable    NewJeans
    ${songAlbum}=    Set Variable    Ditto
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    songName=${songName}   songArtistFullName=${songArtistFullName}    songAlbum=${songAlbum}
    ${resp}=    POST On Session    songs    /addSong    json=${params}    headers=${headers}    expected_status=200
    ${data}=    Get From Dictionary    ${resp.json()}    data
    Dictionary Should Contain Key    ${data}    id
    ${dittoId}=    Get From Dictionary    ${data}    id
    Set Suite Variable    ${ditto}    ${dittoId}
    Log To Console    Song titled ${songName} has ID ${ditto}

    ${songName}=    Set Variable    Addicted
    Set Suite Variable    ${addictedTitle}    ${songName}
    ${songArtistFullName}=    Set Variable    Simple Plan
    ${songAlbum}=    Set Variable    No Pads, No Helmets...Just Balls
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    songName=${songName}   songArtistFullName=${songArtistFullName}    songAlbum=${songAlbum}
    ${resp}=    POST On Session    songs    /addSong    json=${params}    headers=${headers}    expected_status=200
    ${data}=    Get From Dictionary    ${resp.json()}    data
    Dictionary Should Contain Key    ${data}    id
    ${addictedId}=    Get From Dictionary    ${data}    id
    Set Suite Variable    ${addicted}    ${addictedId}
    Log To Console    Song titled ${songName} has ID ${addicted}

    ${songName}=    Set Variable    ihonestlymightjustgiveup
    Set Suite Variable    ${ihonestlymightjustgiveupTitle}    ${songName}
    ${songArtistFullName}=    Set Variable    d0llywood1
    ${songAlbum}=    Set Variable    ihonestlymightjustgiveup
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    songName=${songName}   songArtistFullName=${songArtistFullName}    songAlbum=${songAlbum}
    ${resp}=    POST On Session    songs    /addSong    json=${params}    headers=${headers}    expected_status=200
    ${data}=    Get From Dictionary    ${resp.json()}    data
    Dictionary Should Contain Key    ${data}    id
    ${ihonestlymightjustgiveupId}=    Get From Dictionary    ${data}    id
    Set Suite Variable    ${ihonestlymightjustgiveup}    ${ihonestlymightjustgiveupId}
    Log To Console    Song titled ${songName} has ID ${ihonestlymightjustgiveup}

    ${songName}=    Set Variable    dummySongIdDeleted
    ${songArtistFullName}=    Set Variable    whatever
    ${songAlbum}=    Set Variable    deletedSongs
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    songName=${songName}   songArtistFullName=${songArtistFullName}    songAlbum=${songAlbum}
    ${resp}=    POST On Session    songs    /addSong    json=${params}    headers=${headers}    expected_status=200
    ${data}=    Get From Dictionary    ${resp.json()}    data
    Dictionary Should Contain Key    ${data}    id
    ${dummySongIdDeletedId}=    Get From Dictionary    ${data}    id
    Set Suite Variable    ${dummySongIdDeleted}    ${dummySongIdDeletedId}
    Log To Console    Song titled ${songName} has ID ${dummySongIdDeletedId}
    ${resp}=    DELETE On Session    songs    /deleteSongById/${dummySongIdDeleted}    headers=${headers}    expected_status=200
    ${resp}=    GET On Session    songs    /getSongById/${dummySongIdDeleted}    headers=${headers}    expected_status=404

Setup UserNames
    Set Suite Variable    ${testPerson1}    swetha
    Set Suite Variable    ${testPerson2}    arnold
    Set Suite Variable    ${testPerson3}    kevin

*** Test Cases ***

TestAddProfile200
    Set Suite Variable    ${testPerson1}    swetha
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    userName=${testPerson1}    fullName=Swetha Maramganty    password=nm1
    ${resp}=    POST On Session    profiles    /profile    json=${params}    headers=${headers}    expected_status=200

Test2AddProfile200
    Set Suite Variable    ${testPerson2}    arnold
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    userName=${testPerson2}    fullName=Arnold Rosenbloom    password=nm2
    ${resp}=    POST On Session    profiles    /profile    json=${params}    headers=${headers}    expected_status=200

Test3AddProfile200
    Set Suite Variable    ${testPerson3}    kevin
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    userName=${testPerson3}    fullName=Kevin Bacon    password=nm0000102
    ${resp}=    POST On Session    profiles    /profile    json=${params}    headers=${headers}    expected_status=200

# TestAddProfile400
#     ${headers}=    Create Dictionary    Content-Type=application/json
#     ${params}=    Create Dictionary    fullName=Emma Stone    password=adfadfadf
#     ${resp}=    POST On Session    profiles    /profile    json=${params}    headers=${headers}    expected_status=400

TestFollowProfile200
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    userName=${testPerson1}    friendUserName=${testPerson2}
    ${resp}=    PUT On Session    profiles    /followFriend    json=${params}    headers=${headers}    expected_status=200

Test2FollowProfile200
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    userName=${testPerson1}    friendUserName=${testPerson3}
    ${resp}=    PUT On Session    profiles    /followFriend    json=${params}    headers=${headers}    expected_status=200

Test3FollowProfile200
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    userName=${testPerson2}    friendUserName=${testPerson1}
    ${resp}=    PUT On Session    profiles    /followFriend    json=${params}    headers=${headers}    expected_status=200

# TestFollowProfile400
#     ${headers}=    Create Dictionary    Content-Type=application/json
#     ${params}=    Create Dictionary    userName=${testPerson3}    friendName=${testPerson1}
#     ${resp}=    PUT On Session    profiles    /followFriend    json=${params}    headers=${headers}    expected_status=400

# due to poor instructions on my part, i should not test for what happens if you try to follow yourself

TestFollowProfile404
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    userName=${testPerson3}    friendUserName=dafdfadf
    ${resp}=    PUT On Session    profiles    /followFriend    json=${params}    headers=${headers}    expected_status=404

Test4FollowProfile200
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    userName=${testPerson2}    friendUserName=${testPerson3}
    ${resp}=    PUT On Session    profiles    /followFriend    json=${params}    headers=${headers}    expected_status=200

TestUnfollowProfile200
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    userName=${testPerson2}    friendUserName=${testPerson3}
    ${resp}=    PUT On Session    profiles    /unfollowFriend    json=${params}    headers=${headers}    expected_status=200

# TestUnfollowProfile400
#     ${headers}=    Create Dictionary    Content-Type=application/json
#     ${params}=    Create Dictionary    userName=${testPerson3}    friendName=${testPerson1}
#     ${resp}=    PUT On Session    profiles    /unfollowFriend    json=${params}    headers=${headers}    expected_status=400

# due to poor instructions on my part, i should not test for when I try to unfollow someone who I am not already following
# I didn't properly define what case it should be

# due to poor instructions on my part, i should not test for what happens if you try to unfollow yourself

TestUnfollowProfile404
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    userName=${testPerson3}    friendUserName=adfadffd
    ${resp}=    PUT On Session    profiles    /unfollowFriend    json=${params}    headers=${headers}    expected_status=404

TestLikeSong200
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    userName=${testPerson2}    songId=${turpentine}
    ${resp}=    PUT On Session    profiles    /likeSong    json=${params}    headers=${headers}    expected_status=200

Test2LikeSong200
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    userName=${testPerson2}    songId=${ditto}
    ${resp}=    PUT On Session    profiles    /likeSong    json=${params}    headers=${headers}    expected_status=200

Test3LikeSong200
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    userName=${testPerson3}    songId=${addicted}
    ${resp}=    PUT On Session    profiles    /likeSong    json=${params}    headers=${headers}    expected_status=200

Test4LikeSong200
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    userName=${testPerson3}    songId=${ihonestlymightjustgiveup}
    ${resp}=    PUT On Session    profiles    /likeSong    json=${params}    headers=${headers}    expected_status=200

# TestLikeSong400
#     ${headers}=    Create Dictionary    Content-Type=application/json
#     ${params}=    Create Dictionary    userName=${testPerson3}    song=${dummySongIdDeleted}
#     ${resp}=    PUT On Session    profiles    /likeSong    json=${params}    headers=${headers}    expected_status=400

TestLikeSong404User
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    userName=random    songId=${dummySongIdDeleted}
    ${resp}=    PUT On Session    profiles    /likeSong    json=${params}    headers=${headers}    expected_status=404

TestLikeSong404Song
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    userName=${testPerson3}    songId=${dummySongIdDeleted}
    ${resp}=    PUT On Session    profiles    /likeSong    json=${params}    headers=${headers}    expected_status=404

TestUnlikeSong200
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    userName=${testPerson3}    songId=${ihonestlymightjustgiveup}
    ${resp}=    PUT On Session    profiles    /unlikeSong    json=${params}    headers=${headers}    expected_status=200

# TestUnlikeSong400
#     ${headers}=    Create Dictionary    Content-Type=application/json
#     ${params}=    Create Dictionary    userName=${testPerson3}    song=${dummySongIdDeleted}
#     ${resp}=    PUT On Session    profiles    /unlikeSong    json=${params}    headers=${headers}    expected_status=400

TestUnlikeSong404User
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    userName=random   songId=${dummySongIdDeleted}
    ${resp}=    PUT On Session    profiles    /unlikeSong    json=${params}    headers=${headers}    expected_status=404

TestUnlikeSong404Song
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    userName=${testPerson3}    songId=${dummySongIdDeleted}
    ${resp}=    PUT On Session    profiles    /unlikeSong    json=${params}    headers=${headers}    expected_status=404

TestGetAllFriendsSongs200
    ${userName}=    Set Variable    ${testPerson1}
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${resp}=    GET On Session    profiles    /getAllFriendFavouriteSongTitles/${userName}    headers=${headers}    expected_status=200
    ${data}=    Get From Dictionary    ${resp.json()}    data
    Log To Console    ${data}
    ${friendsCount}=    Get Length    ${data}
    Should Be Equal As Integers    ${friendsCount}    ${2}
    Dictionary Should Contain Key    ${data}    ${testPerson2}
    Dictionary Should Contain Key    ${data}    ${testPerson3}
    ${testPerson2Songs}=    Get From Dictionary    ${data}    ${testPerson2}
    ${testPerson2SongCount}=    Get Length    ${testPerson2Songs}
    Should Be Equal As Integers    ${testPerson2SongCount}    ${2}
    List Should Contain Value    ${testPerson2Songs}    ${turpentineTitle}
    List Should Contain Value    ${testPerson2Songs}    ${dittoTitle}
    ${testPerson3Songs}=    Get From Dictionary    ${data}    ${testPerson3}
    ${testPerson3SongCount}=    Get Length    ${testPerson3Songs}
    Should Be Equal As Integers    ${testPerson3SongCount}    ${1}
    List Should Contain Value    ${testPerson3Songs}    ${addictedTitle}

TestGetAllFriendsSongs200FriendDoesntLikeAnything
    ${userName}=    Set Variable    ${testPerson2}
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${resp}=    GET On Session    profiles    /getAllFriendFavouriteSongTitles/${userName}    headers=${headers}    expected_status=200
    ${data}=    Get From Dictionary    ${resp.json()}    data
    Log To Console    ${data}
    ${friendsCount}=    Get Length    ${data}
    Should Be Equal As Integers    ${friendsCount}    ${1}
    Dictionary Should Contain Key    ${data}    ${testPerson1}
    ${testPerson1Songs}=    Get From Dictionary    ${data}    ${testPerson1}
    ${testPerson1SongCount}=    Get Length    ${testPerson1Songs}
    Should Be Equal As Integers    ${testPerson1SongCount}    ${0}

TestGetAllFriendsSongs200NoFriends
    ${userName}=    Set Variable    ${testPerson3}
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${resp}=    GET On Session    profiles    /getAllFriendFavouriteSongTitles/${userName}    headers=${headers}    expected_status=200
    ${data}=    Get From Dictionary    ${resp.json()}    data
    Log To Console    ${data}
    ${friendsCount}=    Get Length    ${data}
    Should Be Equal As Integers    ${friendsCount}    ${0}

TestGetAllFriendsSongs404
    ${userName}=    Set Variable    adfadfdf
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${resp}=    GET On Session    profiles    /getAllFriendFavouriteSongTitles/${userName}    headers=${headers}    expected_status=404

Test2GetAllFriendsSongs200
    ${songId}=    Set Variable    ${ditto}
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${resp}=    DELETE On Session    songs    /deleteSongById/${songId}    headers=${headers}    expected_status=200
    ${userName}=    Set Variable    ${testPerson1}
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${resp}=    GET On Session    profiles    /getAllFriendFavouriteSongTitles/${userName}    headers=${headers}    expected_status=200
    ${data}=    Get From Dictionary    ${resp.json()}    data
    Log To Console    ${data}
    ${friendsCount}=    Get Length    ${data}
    Should Be Equal As Integers    ${friendsCount}    ${2}
    Dictionary Should Contain Key    ${data}    ${testPerson2}
    Dictionary Should Contain Key    ${data}    ${testPerson3}
    ${testPerson2Songs}=    Get From Dictionary    ${data}    ${testPerson2}
    ${testPerson2SongCount}=    Get Length    ${testPerson2Songs}
    Should Be Equal As Integers    ${testPerson2SongCount}    ${1}
    List Should Not Contain Value    ${testPerson2Songs}    ${dittoTitle}
    List Should Contain Value    ${testPerson2Songs}    ${turpentineTitle}
    ${testPerson3Songs}=    Get From Dictionary    ${data}    ${testPerson3}
    ${testPerson3SongCount}=    Get Length    ${testPerson3Songs}
    Should Be Equal As Integers    ${testPerson3SongCount}    ${1}
    List Should Contain Value    ${testPerson3Songs}    ${addictedTitle}

Test3GetAllFriendsSongs200
    ${songId}=    Set Variable    ${addicted}
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${resp}=    DELETE On Session    songs    /deleteSongById/${songId}    headers=${headers}    expected_status=200
    ${userName}=    Set Variable    ${testPerson1}
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${resp}=    GET On Session    profiles    /getAllFriendFavouriteSongTitles/${userName}    headers=${headers}    expected_status=200
    ${data}=    Get From Dictionary    ${resp.json()}    data
    Log To Console    ${data}
    ${friendsCount}=    Get Length    ${data}
    Should Be Equal As Integers    ${friendsCount}    ${2}
    Dictionary Should Contain Key    ${data}    ${testPerson2}
    Dictionary Should Contain Key    ${data}    ${testPerson3}
    ${testPerson2Songs}=    Get From Dictionary    ${data}    ${testPerson2}
    ${testPerson2SongCount}=    Get Length    ${testPerson2Songs}
    Should Be Equal As Integers    ${testPerson2SongCount}    ${1}
    List Should Contain Value    ${testPerson2Songs}    ${turpentineTitle}
    ${testPerson3Songs}=    Get From Dictionary    ${data}    ${testPerson3}
    ${testPerson3SongCount}=    Get Length    ${testPerson3Songs}
    Should Be Equal As Integers    ${testPerson3SongCount}    ${0}
    List Should Not Contain Value    ${testPerson3Songs}    ${addictedTitle}

# Test2DeleteSongFromMongoDB200
#     ${songId}=    Set Variable    ${ditto}
#     ${headers}=    Create Dictionary    Content-Type=application/json
#     ${resp}=    DELETE On Session    songs    /deleteSongById/${songId}    headers=${headers}    expected_status=200

# TestCheckUser200
#     ${userName}=    Set Variable    ${testPerson1}
#     ${headers}=    Create Dictionary    Content-Type=application/json
#     ${resp}=    GET On Session    profiles    /checkUser/${userName}    headers=${headers}    expected_status=200

# TestCheckUser404
#     ${userName}=    Set Variable    adfadf
#     ${headers}=    Create Dictionary    Content-Type=application/json
#     ${resp}=    GET On Session    profiles    /checkUser/${userName}    headers=${headers}    expected_status=404

# TestCheckSong200
#     ${songId}=    Set Variable    ${turpentine}
#     ${headers}=    Create Dictionary    Content-Type=application/json
#     ${resp}=    GET On Session    profiles    /checkSong/${songId}    headers=${headers}    expected_status=200

# TestCheckSong404
#     ${songId}=    Set Variable    ${dummySongIdDeleted}
#     ${headers}=    Create Dictionary    Content-Type=application/json
#     ${resp}=    GET On Session    profiles    /checkSong/${songId}    headers=${headers}    expected_status=404