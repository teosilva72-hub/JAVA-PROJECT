
/* ******************************************************************************************************** */

/** Checked
 *  
 * @author Wellington da Silva : 2021-05-19
 * @summary Function used to check if element could be enabled by display none / block
 * @since version 1.0
 * @version 1.1 
 * @description display elements
 * @copyright Tracevia S/A 2021
 * @param {string} id element id
 * @param {boolean} bool boolean value to check 
 * @returns {void}
 * 
**/
    function checked(id, bool) {
        var element = document.getElementById(id);    
                  
        if (!bool) {
            element.style.display = "none";
        } else {
            element.style.display = "block";
        }
   }

/* ******************************************************************************************************** */

/** Show elements
 *  
 * @author Wellington da Silva : 2021-05-19
 * @summary Function used to show dashboard hidden elements
 * @since version 1.0
 * @version 1.1 
 * @description to show hidden elements
 * @copyright Tracevia S/A 2021
 * @returns {void}
 * 
**/

   function showElements(){

     document.getElementById("btnLayers").classList.remove("hidden");
     document.getElementById("btnLayers").classList.add("show");
     document.getElementById("btnEquips").classList.remove("hidden");

  }	   
  
  /* ******************************************************************************************************** */