var togglevalue;
var token;
var useremail;
getLocalVariables();
var subject = [];
var views = [];

var hashMapSubject = new Map();
//getLocalVariables();
InboxSDK.load(2, 'sdk_SonarApp_576df08053').then(function (sdk) {
    function openComposeBox(sdk) {
        handler = sdk.Compose.registerComposeViewHandler(function (composeView) {

            var sender_email = composeView.getFromContact().name;
            var days_input = "";
            var hours_input = "";
            var mins_input = "";
            var nudge_ = "";
            var customer_Type = "";
            //let toggle_="off";

            chrome.storage.local.get("customerType", function (items) {
                customer_Type = items["customerType"];
                console.log(customer_Type);

                //customer_Type = "PREMIUM";
                clearLocalVariables();
                console.log('outside', customer_Type);
                if (customer_Type == "NON_PREMIUM") {

                    var code = chrome.extension.getURL("compose_signature.html")
                    console.log(code);
                    var contentStringnew = composeView.getHTMLContent();
                    var sonartext = '<div class="editable" contenteditable="false" style="color:green;font-size:80%; margin-top:250px;padding-left:10px">  The sender of this email has Read Recipients on. Made possible by Sonar<sup>TM</sup></div>';
                    contentStringnew += sonartext;
                    var contenthtml = '<iframe width="500px" height="80px" frameBorder="0" src=' + code + '></iframe>'
                    contentStringnew += contenthtml;
                    composeView.setBodyHTML(contentStringnew);
                    console.log(contentStringnew);



                }
                else if(customer_Type == "PREMIUM"){

                    var code2 = chrome.extension.getURL("Premium_features.html")
                    console.log(code2);
                    var contentStringnew2 = composeView.getHTMLContent();

                    var contenthtml2 = '<div style="position: relative;"><iframe width="390px" height="400px" frameBorder="0" style="position:absolute;" src=' + code2 + ' ></iframe></div>'
                    // var contenthtml2 = '<iframe src="http://URL_HERE.html" onload='javascript:(function(o){o.style.height=o.contentWindow.document.body.scrollHeight+"px";}(this));' style="height:200px;width:100%;border:none;overflow:hidden;"></iframe>'
                    console.log('contenthtml2', contenthtml2);
                    contentStringnew2 += contenthtml2;

                    var pop = composeView.setBodyHTML(contentStringnew2);
                    console.log(contentStringnew2);
                }

            })

            composeView.on('presending', function () {

                
                var uuid = generateUUID();
                console.log('uuid', uuid);
                var subj = composeView.getSubject();
                composeView.insertHTMLIntoBodyAtCursor(`<img id="dnfTrackingPixel" src="http://ec2-18-119-156-157.us-east-2.compute.amazonaws.com:8888/pixel/${uuid}" alt="trackingPixelM">`);
                console.log('added pixel successfully');
                //Post request for pixel
                var token = "";
                var recipients_ = composeView.getToRecipients();
                var emails = [];
                console.log(recipients_[0]);
                console.log(recipients_.emailAddress);

                for (let i = 0; i < recipients_.length; i++) {
                    emails.push(recipients_[i].emailAddress);
                    console.log('added email');
                }
                for (let j = 0; j < emails.length; j++) {
                    console.log('email', emails[j]);
                }
                chrome.storage.local.get("authToken1", function (items) {
                    if (chrome.runtime.lastError) {
                        console.log('key mininput not found');
                        return;
                    }
                    else {

                        token = items["authToken1"];
                        console.log("fecthed token");
                        console.log(token);
                        // Integrating Premium nudge feature
                        chrome.storage.local.get("daysinput", function (items1) {
                            var days_input = "";
                            if (chrome.runtime.lastError) {
                                console.log('key daysinput not found');
                                return;
                            }
                            else {

                                days_input = items1['daysinput'];
                                console.log('else daysinput', days_input);
                                chrome.storage.local.get("hoursinput", function (items) {
                                    var hours_input = "";
                                    if (chrome.runtime.lastError) {
                                        console.log('key hoursinput not found');
                                        return;
                                    }
                                    else {
                                        hours_input = items["hoursinput"];
                                        console.log('else time', hours_input);
                                        chrome.storage.local.get("minsinput", function (items) {
                                            var mins_input = "";
                                            if (chrome.runtime.lastError) {
                                                console.log('key minsinput not found');
                                                return;
                                            }
                                            else {
                                                mins_input = items["minsinput"];
                                                console.log('else minsinput', mins_input);
                                                console.log('fetchcing values');
                                                console.log('lendays', days_input);
                                                console.log('lenhours', hours_input);
                                                console.log('lenmins', mins_input);
                                                console.log('lennudge', nudge_);
                                                chrome.storage.local.get("toggleval", function (items) {
                                                    togglevalue = items["toggleval"];
                                                    var toggle_val;
                                                    if(togglevalue === undefined || togglevalue === false){
                                                        toggle_val = false;
                                                    }
                                                    else{
                                                        toggle_val = true;
                                                    }
                                                //console.log('type', typeof (days_input));
                                                if ((typeof (days_input) == "undefined" && typeof (hours_input) == "undefined" && typeof (mins_input) == "undefined") || (days_input.length == 0 && hours_input.length == 0 && mins_input.length == 0)) {
                                                    nudge_ = false;
                                                }
                                                else {
                                                    nudge_ = true;
                                                }
                                                let headers = new Headers();

                                                headers.set("Authorization", `Bearer ${token}`);
                                                headers.set("Content-Type", 'application/json');
                                                console.log(nudge_);

                                                // days_input=1;
                                                // hours_input=2;
                                                // mins_input =3;
                                                //nudge_ = true;
                                                console.log('days', days_input);
                                                console.log('hours', hours_input);
                                                console.log('mins', mins_input);
                                                console.log('email', emails);
                                                fetch(`https://ec2-18-119-156-157.us-east-2.compute.amazonaws.com:9999/pixel/${subj}/${uuid}`, {
                                                    method: 'POST',
                                                    headers: headers,
                                                    body: JSON.stringify({
                                                        nudge: nudge_,
                                                        day: days_input,
                                                        minute: mins_input,
                                                        hour: hours_input,
                                                        recipientEmails: emails,
                                                        toggle: toggle_val,
                                                    }),
                                                })
                                                    .then(response => {
                                                        if (response.status === 200) {
                                                            console.log('postpixel', response);
                                                            //clearLocalVariables();
                                                            //window.location.reload();

                                                            console.log('page refreshed');
                                                        } else {
                                                            throw new Error('Something went wrong on api server!');
                                                        }
                                                    })
                                                    .then(response => {
                                                        console.debug(response);
                                                    }).catch(error => {
                                                        console.error(error);
                                                  });    

                                                });
                                            }
                                        });
                                    
                                    }
                                });
                            }
                        });
                    



                    }//else ends


                })
                //} //if ends
                // else{
                //   console.log('toggle is off');
                // }
                // }
                // });//chromestoage 
            });//presending end


        });
        function generateUUID() {
            var d = new Date().getTime();
            var uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
                var r = (d + Math.random() * 16) % 16 | 0;
                d = Math.floor(d / 16);
                return (c == 'x' ? r : (r & 0x3 | 0x8)).toString(16);
            });
            return uuid;
        }


        //})
    }

    openComposeBox(sdk);
    //clearLocalVariables();

    function onOpenSentBox(sdk){
        getCustomer(token,useremail);
        console.log("token in sent box", token);
        console.log("useremail in sentbox", useremail);
        sdk.Lists.registerThreadRowViewHandler(function(threadRowView) {
          let routeView = sdk.Router.getCurrentRouteView() ;
          if(routeView.getRouteID() === sdk.Router.NativeRouteIDs.SENT){
          let subj = threadRowView.getSubject();
         console.log(hashMapSubject);
          var isPresent = hashMapSubject.get(subj);
         if(isPresent == 0){
          threadRowView.addLabel({
            title: 'Delivered',
            foregroundColor: '#BBC1CE',
            backgroundColor: '#D1F8D7'
          });
         }
            if(isPresent > 0){
              threadRowView.addLabel({
                title: 'Opened',
                foregroundColor: '#FFFFFF',
                backgroundColor: '#17C631'
          });
        }
        // sdk.Conversations.registerMessageViewHandler(function(messageView) {
        //   let htmlContent = messageView.getBodyElement();
        //   console.log(htmlContent);
      
        // });
      }
        });
      }
      
      onOpenSentBox(sdk)
});


function getCustomer(token,emailId) {
    let statusOK;
    let headers = new Headers();
    headers.set("Authorization", `Bearer ${token}`);
  
    // var $emailId= emailId;
    let url = "https://ec2-18-119-156-157.us-east-2.compute.amazonaws.com:9999/customer/"+emailId;
    //console.log("Full url" + url);
    fetch(url, {
      method: 'GET',
      headers: headers,
    })
  
      .then(response => {
        return response.json();
      }).then(resJson => {
        var customerType = resJson.customerType;
        chrome.storage.local.set({ "customerType": customerType }, function(){
          console.log('customerType has been saved locally');
                  });
        emailData = resJson.emailData;
        var arrayLength = emailData.length;
  
        for (var i = 0; i < arrayLength; i++) {
         hashMapSubject.set(emailData[i].emailHeaderIdentifier,emailData[i].numberOfViews);
        }
      })
      .catch(error => {
        console.error(error);
      });
  }

function getLocalVariables() {
    chrome.storage.local.get("authToken1", function (items) {
        token = items["authToken1"]
    });
    chrome.storage.local.get("useremail", function (items) {
        useremail = items["useremail"]
    });
}

function clearLocalVariables() {
    chrome.storage.local.remove(["daysinput", "hoursinput", "minsinput","toggleval"], function () {
        var error = chrome.runtime.lastError;
        if (error) {
            console.error(error);
        }
        else {
            console.log('all keys removed successfully from local storage');
        }
    });
}