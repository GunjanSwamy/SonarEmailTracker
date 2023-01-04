
let nudge_val = document.getElementById('nudgebutton');
var toggle_value = false ;
console.log('nudge',nudge_val);
if (nudge_val!=null)
{
nudge_val.addEventListener("click", openForm);
}

let close_val = document.getElementById('confirm');
console.log('confirm',close_val);
if(close_val!=null)
{
close_val.addEventListener("click", closeForm);
}

let cancel_nudge = document.getElementById('nudge-cancel');
if (cancel_nudge != null) {
  cancel_nudge.addEventListener("click", cancelNudge);
}

let toggle_open = document.getElementById('togglebutton');
let toggle_check = document.getElementById('tgl-check');
function openForm() {
  document.getElementById("togglepop").style.display = "none";
  document.getElementById("popupForm").style.display = "block";
}

function closeForm() {

  var days_input = document.getElementById("Days").value;
  var hours_input = document.getElementById("Hours").value;
  var min_input = document.getElementById("Mins.").value;


  console.log(days_input);
  console.log(hours_input);
  console.log(min_input);

  chrome.storage.local.set({'daysinput': days_input});
  chrome.storage.local.set({'hoursinput': hours_input});
  chrome.storage.local.set({'minsinput': min_input});
  document.getElementById("popupForm").style.display = "none";
  
  }
function cancelNudge(){
  days_input = 0;
  hour_input = 0;
  min_input = 0;

  document.getElementById("popupForm").style.display = "none";
}

// ---------toggle Popup-------------------------
//let toggle_open = document.getElementById('togglebutton');
//let toggle_check = document.getElementById('tgl-check');

if(toggle_open != null){
  toggle_open.addEventListener("click",openToggle)
}

let toggle_cancel = document.getElementById('tgl-cancel');
if(toggle_cancel != null){
  toggle_cancel.addEventListener("click",cancelToggle)
}

let toggle_confirm = document.getElementById('tgl-confirm');
if(toggle_confirm != null){
  toggle_confirm.addEventListener("click",confirmToggle)
}



function cancelToggle(){
  
  toggle_value = false;
  console.log(toggle_value);
  document.getElementById("togglepop").style.display = "none";
}

function openToggle(){
 document.getElementById('tgl-check').checked = toggle_value;
  
  document.getElementById("popupForm").style.display = "none";
  document.getElementById("togglepop").style.display = "block";
  // toggle_check.checked=false;
 

}

function confirmToggle(){
  console.log("In the end:", toggle_value); 
  // chrome.storage.local.remove(["toggle_key2"], function () {
  //   var error = chrome.runtime.lastError;
  //   if (error) {
  //     console.error(error);
  //   }
  //   else {
  //     console.log('token_key removed successfully from local storage');
  //     chrome.storage.local.set({'toggle_key2': toggle_val });
  //   console.log("Toggle value saved in local storage",toggle_val);
  //   }
  // })
  
  document.getElementById("togglepop").style.display = "none";
  
 

}

if(toggle_check != null){
  toggle_check.addEventListener("click",checkToggle)
}

function checkToggle(){

  if(toggle_check.checked){
    toggle_value = true;
    //document.getElementById("popupForm").style.display = "none";
    console.log("In the end:", toggle_value); 
    chrome.storage.local.set({ "toggleval": toggle_value }, function () {
      console.log('Toggle has been saved locally' + toggle_value);
});
  }
  else{
    toggle_value = false;
    console.log("In the end:", toggle_value); //this is the final check value, Mitali.
    chrome.storage.local.set({ "toggleval": toggle_value }, function () {
      console.log('Toggle has been saved locally' + toggle_value);
});

    //document.getElementById("popupForm").style.display = "block";
  }

  console.log(toggle_value);
}






