/* background.js */
let user_signed_in = false;
const API_KEY = 'AIzaSyAe2pbzvI5JfhsukEHp5p5TZBMsvncsRIk';
const CLIENT_ID = "845486106645-7a0nci01jd5pulh988p2htnske8a6m6p.apps.googleusercontent.com";
const RESPONSE_TYPE = 'id_token';
const REDIRECT_URI = "https://hmcbnndnnghgpjmlmodafegboklpjglc.chromiumapp.org";
const STATE = 'jfkls3n';
const SCOPE = 'openid profile email';
const PROMPT = 'consent';

function create_auth_endpoint() {
    let nonce = encodeURIComponent(Math.random().toString(36).substring(2, 15) + Math.random().toString(36).substring(2, 15));

    let openId_endpoint_url =
        `https://accounts.google.com/o/oauth2/v2/auth
?client_id=${CLIENT_ID}
&response_type=${RESPONSE_TYPE}
&redirect_uri=${REDIRECT_URI}
&scope=${SCOPE}
&state=${STATE}
&nonce=${nonce}
&prompt=${PROMPT}`;

    return openId_endpoint_url;
}

chrome.runtime.onMessage.addListener((request, sender, sendResponse) => {
    if (request.message === 'login') {
        if (user_signed_in) {
            console.log("User is already signed in.");

        } else {
            chrome.identity.launchWebAuthFlow({
                'url': create_auth_endpoint(),
                'interactive': true
            }, function (redirect_url) {
                if (chrome.runtime.lastError) {
                    // problem signing in
                } else {
                    let id_token = redirect_url.substring(redirect_url.indexOf('id_token=') + 9);
                    id_token = id_token.substring(0, id_token.indexOf('&'));
                    const user_info = KJUR.jws.JWS.readSafeJSONString(b64utoutf8(id_token.split(".")[1]));
                    var useremail = user_info.email;
                    var name = user_info.name;
                    var givenname = user_info.given_name;
                    var familyname = user_info.family_name;
                    console.log("Before post auth");
                    let formData = new FormData();
                    let headers = new Headers();
                    headers.set("Authorization", "Basic " + btoa("client123" + ":" + "secret123"));
                    formData.append("grant_type", "social");
                    formData.append("email", useremail);
                    formData.append("firstName", givenname);
                    formData.append("lastName", familyname);
                    formData.append("loginSource", "GOOGLE");

                    //Get access_token
                    fetch("https://ec2-18-119-156-157.us-east-2.compute.amazonaws.com:9999/oauth/token", {
                        method: "POST",

                        headers: headers,
                        body: formData,
                    })
                        .then((response) => response.json())
                        .then((responseJson) => {
                            console.log(responseJson);
                            var access_token = responseJson.access_token;
                            console.log(responseJson.access_token)
                            chrome.storage.local.set({ "authToken1": access_token }, function () {
                                console.log('Token has been saved locally');
                            });
                            chrome.storage.local.get("authToken1", function (items) {
                                let token = items["authToken1"]
                                GetCustomer(token, useremail);
                            })
                            chrome.storage.local.set({ "useremail": useremail }, function(){
                                console.log('User email has been saved locally');
                            });

                        })
                        .catch((error) => {
                            console.error(error);
                        });
                    console.log("After post auth");
                    console.log(user_info);
                    console.log(user_info.email);
                    console.log(user_info.family_name);
                    console.log(user_info.given_name);
                    if ((user_info.iss === 'https://accounts.google.com' || user_info.iss === 'accounts.google.com')
                        && user_info.aud === CLIENT_ID) {
                        console.log("User successfully signed in.");
                        window.open("https://mail.google.com/", '_blank');
                        user_signed_in = true;
                        //var toggle_value = "off";
                        chrome.storage.local.set({ 'toggle_key': "off" });
                        chrome.browserAction.setPopup({ popup: './popup-signed-in.html' }, () => {
                            sendResponse('success');
                        });
                    } else {
                        // invalid credentials
                        console.log("Invalid credentials.");
                    }
                }
            });

            return true;
        }
    }
    else {
        console.log("in else part");
    }
    /* else if (request.message === 'isUserSignedIn') {
        sendResponse(is_user_signed_in());
    } */
});


chrome.runtime.onInstalled.addListener(function () {
    //alert('You just made the best decision of today, by installing SONAR! ');

    chrome.tabs.create({
        url: 'popup.html',
        active: true
    });

    return false;
});

//Get customerapi call
function GetCustomer(token, emailId) {
    let headers = new Headers();
    console.log('inside getcustomer', token);
    headers.set("Authorization", `Bearer ${token}`);

    //var $emailId_= emailId;
    fetch(`https://ec2-18-119-156-157.us-east-2.compute.amazonaws.com:9999/customer/${emailId}`, {
        method: 'GET',
        headers: headers,
    })
        .then(response => {
            if (response.status === 200) {
                // console.log('getcustomer',response.json());
                return response.json();
            } else {
                throw new Error('Something went wrong on api server!');
            }
        })
        .then(resJson => {
            var customerType = resJson.customerType;
            chrome.storage.local.set({ "customerType": customerType }, function () {
                console.log('CustomerType stored',);
            });
            console.log(customerType);
        })
    // .then(response => {
    //   console.debug(response);
    // }).catch(error => {
    //   console.error(error);
    // });
}
