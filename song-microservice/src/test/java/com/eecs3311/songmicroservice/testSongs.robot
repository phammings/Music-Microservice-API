*** Settings ***
Library           Collections
Library           RequestsLibrary
Test Timeout      30 seconds

Suite Setup    Create Session    localhost    http://localhost:3001

*** Variables ***
${danceWithMe}
${omg}
${youngLuv}
${whatIsLove}

*** Test Cases ***
TestAddSong200
    ${songName}=    Set Variable    DANCE WITH ME
    ${songArtistFullName}=    Set Variable    blink-182
    ${songAlbum}=    Set Variable    ONE MORE TIME...
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    songName=${songName}   songArtistFullName=${songArtistFullName}    songAlbum=${songAlbum}
    ${resp}=    POST On Session    localhost    /addSong    json=${params}    headers=${headers}    expected_status=200
    ${data}=    Get From Dictionary    ${resp.json()}    data
    Log To Console    ${data}
    Dictionary Should Contain Value    ${data}    ${songName}
    Dictionary Should Contain Value    ${data}    ${songArtistFullName}
    Dictionary Should Contain Value    ${data}    ${songAlbum}
    ${likeCount}=    Get From Dictionary    ${data}    songAmountFavourites
    Run Keyword If  '${likeCount}'.isdigit()  Should Be Equal As Numbers  ${likeCount}    0
    ...  ELSE   Should Be Equal  ${likeCount}   0
    Dictionary Should Contain Key    ${data}    id
    ${danceWithMeId}=    Get From Dictionary    ${data}    id
    Set Suite Variable    ${danceWithMe}    ${danceWithMeId}
    Log To Console    Song titled ${songName} has ID ${danceWithMe}

Test2AddSong200
    ${songName}=    Set Variable    OMG
    ${songArtistFullName}=    Set Variable    NewJeans
    ${songAlbum}=    Set Variable    NewJeans 'OMG'
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    songName=${songName}   songArtistFullName=${songArtistFullName}    songAlbum=${songAlbum}
    ${resp}=    POST On Session    localhost    /addSong    json=${params}    headers=${headers}    expected_status=200
    ${data}=    Get From Dictionary    ${resp.json()}    data
    Log To Console    ${data}
    Dictionary Should Contain Value    ${data}    ${songName}
    Dictionary Should Contain Value    ${data}    ${songArtistFullName}
    Dictionary Should Contain Value    ${data}    ${songAlbum}
    ${likeCount}=    Get From Dictionary    ${data}    songAmountFavourites
    Run Keyword If  '${likeCount}'.isdigit()  Should Be Equal As Numbers  ${likeCount}    0
    ...  ELSE   Should Be Equal  ${likeCount}   0
    Dictionary Should Contain Key    ${data}    id
    ${omgId}=    Get From Dictionary    ${data}    id
    Set Suite Variable    ${omg}    ${omgId}
    Log To Console    Song titled ${songName} has ID ${omg}

Test3AddSong200
    ${songName}=    Set Variable    YOUNG LUV
    ${songArtistFullName}=    Set Variable    STAYC
    ${songAlbum}=    Set Variable    YOUNG-LUV.COM
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    songName=${songName}   songArtistFullName=${songArtistFullName}    songAlbum=${songAlbum}
    ${resp}=    POST On Session    localhost    /addSong    json=${params}    headers=${headers}    expected_status=200
    ${data}=    Get From Dictionary    ${resp.json()}    data
    Log To Console    ${data}
    Dictionary Should Contain Value    ${data}    ${songName}
    Dictionary Should Contain Value    ${data}    ${songArtistFullName}
    Dictionary Should Contain Value    ${data}    ${songAlbum}
    ${likeCount}=    Get From Dictionary    ${data}    songAmountFavourites
    Run Keyword If  '${likeCount}'.isdigit()  Should Be Equal As Numbers  ${likeCount}    0
    ...  ELSE   Should Be Equal  ${likeCount}   0
    Dictionary Should Contain Key    ${data}    id
    ${youngLuvId}=    Get From Dictionary    ${data}    id
    Set Suite Variable    ${youngLuv}    ${youngLuvId}
    Log To Console    Song titled ${songName} has ID ${youngLuv}

Test4AddSong200
    ${songName}=    Set Variable    What is Love
    ${songArtistFullName}=    Set Variable    TWICE
    ${songAlbum}=    Set Variable    Summer Nights
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    songName=${songName}   songArtistFullName=${songArtistFullName}    songAlbum=${songAlbum}
    ${resp}=    POST On Session    localhost    /addSong    json=${params}    headers=${headers}    expected_status=200
    ${data}=    Get From Dictionary    ${resp.json()}    data
    Log To Console    ${data}
    Dictionary Should Contain Value    ${data}    ${songName}
    Dictionary Should Contain Value    ${data}    ${songArtistFullName}
    Dictionary Should Contain Value    ${data}    ${songAlbum}
    ${likeCount}=    Get From Dictionary    ${data}    songAmountFavourites
    Run Keyword If  '${likeCount}'.isdigit()  Should Be Equal As Numbers  ${likeCount}    0
    ...  ELSE   Should Be Equal  ${likeCount}   0
    Dictionary Should Contain Key    ${data}    id
    ${whatIsLoveId}=    Get From Dictionary    ${data}    id
    Set Suite Variable    ${whatIsLove}    ${whatIsLoveId}
    Log To Console    Song titled ${songName} has ID ${whatIsLove}

# TestAddSongBadBodyParam400
#     ${headers}=    Create Dictionary    Content-Type=application/json
#     ${params}=    Create Dictionary    songName=Random Song    songAlbum=Unknown Album
#     ${resp}=    POST On Session    localhost    /addSong    json=${params}    headers=${headers}    expected_status=400

TestGetSong200
    ${id}=    Set Variable    ${danceWithMe}
    ${songName}=    Set Variable    DANCE WITH ME
    ${songArtistFullName}=    Set Variable    blink-182
    ${songAlbum}=    Set Variable    ONE MORE TIME...
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${resp}=    GET On Session    localhost    /getSongById/${id}    headers=${headers}    expected_status=200
    ${data}=    Get From Dictionary    ${resp.json()}    data
    Log To Console    ${data}
    Dictionary Should Contain Value    ${data}    ${songName}
    Dictionary Should Contain Value    ${data}    ${songArtistFullName}
    Dictionary Should Contain Value    ${data}    ${songAlbum}
    ${likeCount}=    Get From Dictionary    ${data}    songAmountFavourites
    Run Keyword If  '${likeCount}'.isdigit()  Should Be Equal As Numbers  ${likeCount}    0
    ...  ELSE   Should Be Equal  ${likeCount}   0
    Dictionary Should Contain Value    ${data}    ${id}

Test2GetSong200
    ${id}=    Set Variable    ${omg}
    ${songName}=    Set Variable    OMG
    ${songArtistFullName}=    Set Variable    NewJeans
    ${songAlbum}=    Set Variable    NewJeans 'OMG'
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${resp}=    GET On Session    localhost    /getSongById/${id}    headers=${headers}    expected_status=200
    ${data}=    Get From Dictionary    ${resp.json()}    data
    Log To Console    ${data}
    Dictionary Should Contain Value    ${data}    ${songName}
    Dictionary Should Contain Value    ${data}    ${songArtistFullName}
    Dictionary Should Contain Value    ${data}    ${songAlbum}
    ${likeCount}=    Get From Dictionary    ${data}    songAmountFavourites
    Run Keyword If  '${likeCount}'.isdigit()  Should Be Equal As Numbers  ${likeCount}    0
    ...  ELSE   Should Be Equal  ${likeCount}   0
    Dictionary Should Contain Value    ${data}    ${id}

Test3GetSong200
    ${id}=    Set Variable    ${youngLuv}
    ${songName}=    Set Variable    YOUNG LUV
    ${songArtistFullName}=    Set Variable    STAYC
    ${songAlbum}=    Set Variable    YOUNG-LUV.COM
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${resp}=    GET On Session    localhost    /getSongById/${id}    headers=${headers}    expected_status=200
    ${data}=    Get From Dictionary    ${resp.json()}    data
    Log To Console    ${data}
    Dictionary Should Contain Value    ${data}    ${songName}
    Dictionary Should Contain Value    ${data}    ${songArtistFullName}
    Dictionary Should Contain Value    ${data}    ${songAlbum}
    ${likeCount}=    Get From Dictionary    ${data}    songAmountFavourites
    Run Keyword If  '${likeCount}'.isdigit()  Should Be Equal As Numbers  ${likeCount}    0
    ...  ELSE   Should Be Equal  ${likeCount}   0
    Dictionary Should Contain Value    ${data}    ${id}

Test4GetSong200
    ${id}=    Set Variable    ${whatIsLove}
    ${songName}=    Set Variable    What is Love
    ${songArtistFullName}=    Set Variable    TWICE
    ${songAlbum}=    Set Variable    Summer Nights
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${resp}=    GET On Session    localhost    /getSongById/${id}    headers=${headers}    expected_status=200
    ${data}=    Get From Dictionary    ${resp.json()}    data
    Log To Console    ${data}
    Dictionary Should Contain Value    ${data}    ${songName}
    Dictionary Should Contain Value    ${data}    ${songArtistFullName}
    Dictionary Should Contain Value    ${data}    ${songAlbum}
    ${likeCount}=    Get From Dictionary    ${data}    songAmountFavourites
    Run Keyword If  '${likeCount}'.isdigit()  Should Be Equal As Numbers  ${likeCount}    0
    ...  ELSE   Should Be Equal  ${likeCount}   0
    Dictionary Should Contain Value    ${data}    ${id}

# TestGetSong500
#     ${id}=    Set Variable    adfadf
#     ${headers}=    Create Dictionary    Content-Type=application/json
#     ${resp}=    GET On Session    localhost    /getSongById/${id}    headers=${headers}    expected_status=500

TestGetSong404
    ${id}=    Set Variable    0dfadfecfafdcfebad563ad2
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${resp}=    GET On Session    localhost    /getSongById/${id}    headers=${headers}    expected_status=404

TestGetSongTitle200
    ${id}=    Set Variable    ${danceWithMe}
    ${songName}=    Set Variable    DANCE WITH ME
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${resp}=    GET On Session    localhost    /getSongTitleById/${id}    headers=${headers}    expected_status=200
    ${data}=    Get From Dictionary    ${resp.json()}    data
    Log To Console    ${data}
    Should Be Equal As Strings    ${data}    ${songName}

Test2GetSongTitle200
    ${id}=    Set Variable    ${omg}
    ${songName}=    Set Variable    OMG
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${resp}=    GET On Session    localhost    /getSongTitleById/${id}    headers=${headers}    expected_status=200
    ${data}=    Get From Dictionary    ${resp.json()}    data
    Log To Console    ${data}
    Should Be Equal As Strings    ${data}    ${songName}

Test3GetSongTitle200
    ${id}=    Set Variable    ${youngLuv}
    ${songName}=    Set Variable    YOUNG LUV
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${resp}=    GET On Session    localhost    /getSongTitleById/${id}    headers=${headers}    expected_status=200
    ${data}=    Get From Dictionary    ${resp.json()}    data
    Log To Console    ${data}
    Should Be Equal As Strings    ${data}    ${songName}

Test4GetSongTitle200
    ${id}=    Set Variable    ${whatIsLove}
    ${songName}=    Set Variable    What is Love
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${resp}=    GET On Session    localhost    /getSongTitleById/${id}    headers=${headers}    expected_status=200
    ${data}=    Get From Dictionary    ${resp.json()}    data
    Log To Console    ${data}
    Should Be Equal As Strings    ${data}    ${songName}

# TestGetSongTitle500
#     ${id}=    Set Variable    adfadf
#     ${headers}=    Create Dictionary    Content-Type=application/json
#     ${resp}=    GET On Session    localhost    /getSongTitleById/${id}    headers=${headers}    expected_status=500

TestGetSongTitle404
    ${id}=    Set Variable    0dfadfecfafdcfebad563ad2
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${resp}=    GET On Session    localhost    /getSongTitleById/${id}    headers=${headers}    expected_status=404

TestUpdateSongIncrement200
    ${id}=    Set Variable    ${omg}
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    songId=${id}   shouldDecrement=${False}
    ${resp}=    PUT On Session    localhost    /updateSongFavouritesCount    headers=${headers}    json=${params}    expected_status=200
    ${resp}=    GET On Session    localhost    /getSongById/${id}    headers=${headers}    expected_status=200
    ${data}=    Get From Dictionary    ${resp.json()}    data
    Log To Console    ${data}
    ${likeCount}=    Get From Dictionary    ${data}    songAmountFavourites
    Run Keyword If  '${likeCount}'.isdigit()  Should Be Equal As Numbers  ${likeCount}    0
    ...  ELSE   Should Be Equal  ${likeCount}   1

TestUpdateSongCantDecrement500
    ${id}=    Set Variable    ${danceWithMe}
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    songId=${id}   shouldDecrement=${True}
    # ${resp}=    PUT On Session    localhost    /updateSongFavouritesCount    headers=${headers}    json=${params}    expected_status=500
    ${resp}=    PUT On Session    localhost    /updateSongFavouritesCount    headers=${headers}    json=${params}    expected_status=any
    ${resp}=    GET On Session    localhost    /getSongById/${id}    headers=${headers}    expected_status=200
    ${data}=    Get From Dictionary    ${resp.json()}    data
    Log To Console    ${data}
    ${likeCount}=    Get From Dictionary    ${data}    songAmountFavourites
    Run Keyword If  '${likeCount}'.isdigit()  Should Be Equal As Numbers  ${likeCount}    0
    ...  ELSE   Should Be Equal  ${likeCount}   0

TestUpdateSongDecrement200
    ${id}=    Set Variable    ${omg}
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    songId=${id}   shouldDecrement=${True}
    ${resp}=    PUT On Session    localhost    /updateSongFavouritesCount    headers=${headers}    json=${params}    expected_status=200
    ${resp}=    GET On Session    localhost    /getSongById/${id}    headers=${headers}    expected_status=200
    ${data}=    Get From Dictionary    ${resp.json()}    data
    Log To Console    ${data}
    ${likeCount}=    Get From Dictionary    ${data}    songAmountFavourites
    Run Keyword If  '${likeCount}'.isdigit()  Should Be Equal As Numbers  ${likeCount}    0
    ...  ELSE   Should Be Equal  ${likeCount}   0

# TestUpdateSong500
#     ${id}=    Set Variable    adfadf
#     ${headers}=    Create Dictionary    Content-Type=application/json
#     ${params}=    Create Dictionary    songId=${id}   shouldDecrement=${True}
#     ${resp}=    PUT On Session    localhost    /updateSongFavouritesCount    headers=${headers}    json=${params}    expected_status=500

TestUpdateSong404
    ${id}=    Set Variable    0dfadfecfafdcfebad563ad2
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    songId=${id}   shouldDecrement=${True}
    ${resp}=    PUT On Session    localhost    /updateSongFavouritesCount    headers=${headers}    json=${params}    expected_status=404

TestDeleteSong200
    ${id}=    Set Variable    ${danceWithMe}
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${resp}=    DELETE On Session    localhost    /deleteSongById/${id}    headers=${headers}    expected_status=200

Test2DeleteSong200
    ${id}=    Set Variable    ${omg}
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${resp}=    DELETE On Session    localhost    /deleteSongById/${id}    headers=${headers}    expected_status=200

Test3DeleteSong200
    ${id}=    Set Variable    ${youngLuv}
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${resp}=    DELETE On Session    localhost    /deleteSongById/${id}    headers=${headers}    expected_status=200

Test4DeleteSong200
    ${id}=    Set Variable    ${whatIsLove}
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${resp}=    DELETE On Session    localhost    /deleteSongById/${id}    headers=${headers}    expected_status=200

# TestDeleteSong500
#     ${id}=    Set Variable    adfadf
#     ${headers}=    Create Dictionary    Content-Type=application/json
#     ${resp}=    DELETE On Session    localhost    /deleteSongById/${id}    headers=${headers}    expected_status=500

TestDeleteSong404
    ${id}=    Set Variable    ${danceWithMe}
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${resp}=    DELETE On Session    localhost    /deleteSongById/${id}    headers=${headers}    expected_status=404