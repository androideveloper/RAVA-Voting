function printReceipt() {
	var textElem = document.getElementById("receipt").innerHTML;
	var iamgeId = document.getElementById("aftervote:qrcode");
	var imagObject = new Image();
	imagObject = iamgeId;
	var originalImage = '<img id="imageViewer" src="' + imagObject.src
			+ '" height="' + imagObject.height + '" width="' + imagObject.width
			+ '" />';

	popup = window.open('', 'popup',
			'toolbar=no,menubar=no,width=300,height=300');
	popup.document.open();
	popup.document
			.write("<html><head><title>Receipt print</title></head><body onload='print()'>");
	popup.document.write(textElem);
	popup.document.write(originalImage);
	popup.document.write("</body></html>");
	popup.document.close();
	return true;
}