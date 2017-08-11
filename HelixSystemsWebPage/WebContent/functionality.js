/**
 * Will toggle the visibility of the popup-box when a hostname is 
 * clicked.
 * @param id: an id that represents the host.
 * @returns void
 */
function toggle_visibility(id) {
	var popUp = document.getElementById(id);
    if(popUp.style.display == 'block'){
    	popUp.style.display = 'none';
    	}else
         popUp.style.display = 'block';
}
		
/**
 * Filters the System Overview table based on the characters that are 
 * inputed into the search bar.
 * @returns void
 */
function searchFilter() {
	// Declare variables 
	var input, filter, table, tr, td, i;
	input = document.getElementById("myInput");
	filter = input.value.toUpperCase(); 
	table = document.getElementById("system_overview");
	tr = table.getElementsByClassName("rows");

	// Loop through all table rows, and hide those who don't match the search query
	for (i = 0; i < tr.length; i++) {
	td = tr[i].getElementsByTagName("td")[1];
	if (td) {
		if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
			tr[i].style.display = "";
			} else {
				tr[i].style.display = "none";
			}
		} 
	}
}