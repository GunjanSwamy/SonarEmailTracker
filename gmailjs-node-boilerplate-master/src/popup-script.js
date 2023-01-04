/* popup-script.js */
// document.querySelector('div')
//   .addEventListener('click', function () {
// 	  chrome.runtime.sendMessage({message : 'login'},function(response){
// 		   if (response === 'success') window.close();
// 	  });
//   });
  
function myFunction() {
	alert('It was clicked');
}
Anchor.addEventListener('click', function () {
	chrome.runtime.sendMessage({ message: 'login' }, function (response) {
		if (response === 'success') window.close();
	});
});