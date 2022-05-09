
 $(function() {
    $( ".datepicker" ).datepicker({
    	dateFormat: 'dd/mm/yy HH:mm:ss',    
    	yearRange: "-5:+2",
        showButtonPanel:true,        
        changeMonth: true,
        changeYear: true,
        showOtherMonths: true,
        selectOtherMonths: true
      }).focus();
    }).on('focus', '.datepicker', function () {
        var me = $(".datepicker");
        me.mask('99/99/9999 99:99:99');		
 });
	
$.fn.datepickerLocale = function(locale) {
   $.datepicker.setDefaults($.datepicker.regional[locale]);
};
