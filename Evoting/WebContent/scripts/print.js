function printReceipt() {
	var textElem = document.getElementById("receipt").innerHTML;

	popup = window.open('', 'popup',
			'toolbar=no,menubar=no,width=300,height=300');
	popup.document.open();
	popup.document
			.write("<html><head><title>Receipt print</title></head><body onload='print()'>");
	popup.document.write(textElem);
	popup.document.write("</body></html>");
	popup.document.close();
	return true;
}