//////////////////////////////reload////////////////////////
function reload(){
	document.location.reload(false);
}

//////////////////////////////////////////////////////
function resetForm(){
	$('form-cadastro').each (function(){
  this.reset();
});
}
function formFile(){
	$('formFile').each (function(){
  this.reset();
});
}
function resetFormFile(){
	$('listandoFile').each (function(){
  this.reset();

});
}
 //////////////////////////////////////////////////////////////
//DATEPICKER GERAL
$(function() {
    $( ".datepicker" ).datepicker({
		//showOn: 'focus',
        showButtonPanel:true,
		changeMonth: false,
        changeYear: false,
		dateFormat: 'dd/mm/yy',
		//minDate: '12/12/2000',
        //maxDate: '12/12/2020',
		numberOfMonths: 1,
		minDate: 0,
		maxDate: 360
    }).focus();
    }).on('focus', '.datepicker', function () {
        var me = $(".datepicker");
        me.mask('99/99/9999');
	});
	
	//DATEPICKER EVENTOS DATA INICIAL
$(function() {
    $( ".datepickerStart" ).datepicker({
		//showOn: 'focus',
        showButtonPanel:true,
		changeMonth: false,
        changeYear: false,
		dateFormat: 'dd/mm/yy',
		//minDate: '12/12/2000',
        //maxDate: '12/12/2020',
		numberOfMonths: 1,
		minDate: -360,
		maxDate: 0
    }).focus();
    }).on('focus', '.datepickerStart', function () {
        var me = $(".datepickerStart");
        me.mask('99/99/9999');
    });
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/* Esconder Divs Tab1, Tab2 e Tab3 -->*/
		var table;
		function onload(){
		
        	document.getElementById("tab2").style.display = "none";
	    	document.getElementById("tab3").style.display = "none";

			document.getElementById("btn1").style.background = "blue";	//btn1 form1
			document.getElementById("btn1").style.border = "none";	//bt1 form1
			

        }
	
		//Quando clicar no botão aparecerá a div correspondente
		function mostrarTab1(){
			document.getElementById("btn1").style.background = "blue";	//btn1 form1
			document.getElementById("btn1").style.border = "none";	//bt1 form1
			
			document.getElementById("btn2").style.background = "";	//btn2 form2
			document.getElementById("btn2").style.border = "";	//bt2 form2
			
			document.getElementById("btn3").style.background = "";	//btn3 form3
			document.getElementById("btn3").style.border = "";	//bt3 form3
			
		    document.getElementById("tab3").style.display = "none";		
		    document.getElementById("tab2").style.display = "none";
		    document.getElementById("tab1").style.display = "block";

			
		}
		//Quando clicar no botão aparecerá a div correspondente
		function mostrarTab2(){
		    document.getElementById("tab3").style.display = "none";		
		    document.getElementById("tab2").style.display = "block";
 			document.getElementById("tab1").style.display = "none";

			document.getElementById("btn1").style.background = "";	//btn1 form1
			document.getElementById("btn1").style.border = "";	//bt1 form1
			
			document.getElementById("btn2").style.background = "blue";	//btn2 form2
			document.getElementById("btn2").style.border = "none";	//bt2 form2
			
			document.getElementById("btn3").style.background = "";	//btn3 form3
			document.getElementById("btn3").style.border = "";	//bt3 form3
			
			
		}
		//Quando clicar no botão aparecerá a div correspondente
		function mostrarTab3(){
		    document.getElementById("tab3").style.display = "block";		
		    document.getElementById("tab2").style.display = "none";
		    document.getElementById("tab1").style.display = "none";

			document.getElementById("btn1").style.background = "";	//btn1 form1
			document.getElementById("btn1").style.border = "";	//bt1 form1
			
			document.getElementById("btn2").style.background = "";	//btn2 form2
			document.getElementById("btn2").style.border = "";	//bt2 form2
			
			document.getElementById("btn3").style.background = "blue";	//btn3 form3
			document.getElementById("btn3").style.border = "none";	//bt3 form3
		}
  //////////////////////////////////////////////////////////////////////////
 ////////////////INÍCIO DAS TABELAS///////////////// INÍCIO DAS TABELAS////
/////ESCONDER TABELAS/////////////////////////////////////////////////////
function bloquerTable(){
	
	//ESCONDER A TABELA REAL///////////////////////////////////////
	document.getElementById("tabelaReal").style.display = "none";
		
	//////TRAZER A TABELA OCULTA BLOQUEADA////////////////////////
	var element = document.getElementById("tabelaOculta");
 	element.classList.remove("testeTable");
}
///////////////////////////////////////////////////////////////////////////////////
		
		//Configuração da tabela oculta
		function tableBloqueada(){
			$(document).ready(function() {
			  	$('#occurrence-tables').DataTable({
					language: {
						"search": "",
				        searchPlaceholder: "Search"
				    },
					"select": false,
				    "Width": true,			  	   	
				    "scrollY": "68vh",
					"scrollCollapse": false,
					"paging": false,
					"bInfo" : false,
					fixedColumns: true
				})   
			});
		}
/////////////////////////////////////////////////////////////////////////////////////
		//configuração da tabela real
		function tabelaReal(){
			$(document).ready(function teste() {
		   		var table = $('#occurrence-table').DataTable({
					language: {
						"search": "",
				        searchPlaceholder: "Search"
				    },		
		    		"select": true,
				    "autoWidth": true,			  	   	
				    "scrollY": "68vh",
					"scrollCollapse": true,
					"paging": false,
					"bInfo" : false
							
		    	});
				$('.dataTables_length').removeClass('bs-select');			   
			});
		}
		//TROCA DE BOTÕES 1
		function hiddenAnular(){
			document.getElementById("anularAtual").style.display = "none";
			document.getElementById("anularAtualHidden").style.display = "block";			
		}
		//TROCA DO BOTÃO 2
		function hiddenAnular2(){
			document.getElementById("anularAtual").style.display = "none";
			document.getElementById("anularAtualHidden").style.display = "none";
			document.getElementById("anularDepois").style.display = "block";
		}
		function displayPdf(){
			document.getElementById("iconPdf").style.display = "block";
		}
		function hiddenPdf(){
			document.getElementById("iconPdf").style.display = "none";
		}
/////////////////////////////////////////////////////////////////////////////
		//Levar informações da tabela para os inputs
		function selecaoTable(){
			//Método para pegar id da mensagem na seleção de uma row
	   		$('#occurrence-table tbody').on('click', 'tr', function () {
		   	//Get table row
		   	var row = $(this);
	     	//Check if table row is selected on not (Change state) (true or false)
	      	if(!row.hasClass('selected')){
		      	selected = true;
				//document.getElementById("iconPdf").style.display = "block";
				hiddenAnular();			 
				
		    }else{
			
				selected = false;
				
			}
	          		    
	   	   //Get table id on select Row		  
		   var tableData = $(this).children("td").map(function() {
            return $(this).text();
        }).get();
        	idMessage = tableData[0]; // td posição 0 da row (Primeira posição)
        	//Chamar Action do Botão Hidden
        	document.getElementById('formId:hdnBtn').click(); 
	        	//Passar o id para dialog
	        	$('#selectedId').text(idMessage);
			});
		}
			//Id do inputHidden para passar valor para OccrrenceBean (Bean)		 
	   var idMessage, selected;
//Método para Criar a datatable JQuery (Quando for selection será definida na página)
	   
			//Método para passar o valor selecionado
		 function getMessageId() {
		 document.getElementById("formId:idMessage").value = idMessage;	
		 document.getElementById("formId:checked").value = selected;					
	   }
  /////////////////////////////////////////////////////////////////////////////////////
//FUNÇÃO DE VERIFICAÇÃO DE DATA E HORA////////////////////////////////////////////////

function eventValidator(){
	
	//declaração de variaveis data
	
	//inicial
	var eventDateStart = document.getElementById("dataInicial").value;
	var eventHourStart = document.getElementById("horaInicial").value;
	var eventMinuteStart = document.getElementById("minutoInicial").value;
	var eventStart = (eventDateStart + eventHourStart + eventMinuteStart);
	
	//final
	var eventDateEnd = document.getElementById("dataFinal").value;
	var eventHourEnd = document.getElementById("horaFinal").value;
	var eventMinuteEnd = document.getElementById("minutoFinal").value;
	var eventEnd = (eventDateEnd + eventHourEnd + eventMinuteEnd);
	
	if(eventStart != "" && eventEnd==""){

		//mostra borda verde
		document.getElementById("dataInicial").style.border = "";
		document.getElementById("horaInicial").style.border = "";
		document.getElementById("minutoInicial").style.border = "";
		//oculta bordas
		document.getElementById("dataFinal").style.border = "none";
		document.getElementById("horaFinal").style.border = "none";
		document.getElementById("minutoFinal").style.border = "none";
		//oculto o erro
		document.getElementById("errorEvent1").style.display = "none";
		document.getElementById("errorEvent2").style.display = "none";
		document.getElementById("errorEvent3").style.display = "none";
		//save
		document.getElementById("saveModal").style.display = "block";
		document.getElementById("saveTextModal").style.display = "none";
		document.getElementById("SaveModalExit").style.display = "block";
		document.getElementById("saveModalVoltar").style.display = "none";
		document.getElementById("saveBtn").style.display = "block";
		//edit
		document.getElementById("editModal").style.display = "block";
		document.getElementById("editorTextModal").style.display = "none";
		document.getElementById("editModalExit").style.display = "block";
		document.getElementById("editModalVoltar").style.display = "none";
		document.getElementById("editarBtn").style.display = "block";
		
		comparationDate();
		
	}else if(eventStart <= eventEnd){
		//borda verde
		document.getElementById("dataInicial").style.border = "";
		document.getElementById("horaInicial").style.border = "";
		document.getElementById("minutoInicial").style.border = "";
		//borda verde
		document.getElementById("dataFinal").style.border = "";
		document.getElementById("horaFinal").style.border = "";
		document.getElementById("minutoFinal").style.border = "";
		//oculta mensagem de erro
		document.getElementById("errorEvent1").style.display = "none";
		document.getElementById("errorEvent2").style.display = "none";
		document.getElementById("errorEvent3").style.display = "none";
		//save
		document.getElementById("saveModal").style.display = "block";
		document.getElementById("saveTextModal").style.display = "none";
		document.getElementById("SaveModalExit").style.display = "block";
		document.getElementById("saveModalVoltar").style.display = "none";
		document.getElementById("saveBtn").style.display = "block";
		//edit
		document.getElementById("editModal").style.display = "block";
		document.getElementById("editorTextModal").style.display = "none";
		document.getElementById("editModalExit").style.display = "block";
		document.getElementById("editModalVoltar").style.display = "none";
		document.getElementById("editarBtn").style.display = "block";
		
		comparationDate();
	
	}else if(eventStart > eventEnd){
		
		//borda vermelha de erro
		document.getElementById("dataInicial").style.border = "";
		document.getElementById("horaInicial").style.border = "";
		document.getElementById("minutoInicial").style.border = "";
		//mensagem de erro
		document.getElementById("errorEvent1").style.display = "block";
		document.getElementById("errorEvent2").style.display = "block";
		document.getElementById("errorEvent3").style.display = "block";
		//save
		document.getElementById("saveModal").style.display = "none";
		document.getElementById("saveTextModal").style.display = "block";
		document.getElementById("SaveModalExit").style.display = "none";
		document.getElementById("saveModalVoltar").style.display = "block";
		document.getElementById("saveBtn").style.display = "none";
		//edit
		document.getElementById("editModal").style.display = "none";
		document.getElementById("editorTextModal").style.display = "block";
		document.getElementById("editModalExit").style.display = "none";
		document.getElementById("editModalVoltar").style.display = "block";
		document.getElementById("editarBtn").style.display = "none";
		
		return mostrarTab1();
	}
		
}

function TrackValidator(){
	
	//declaração de variaveis data
	
	//inicial
	var trackDateStart = document.getElementById("trackDateStart").value;
	var trackHourStart = document.getElementById("trackHourStart").value;
	var trackMinuteStart = document.getElementById("trackMinuteStart").value;
	var trackStart = (trackDateStart + trackHourStart + trackMinuteStart);
	
	//final
	var trackDateEnd = document.getElementById("trackDateEnd").value;
	var trackHourEnd = document.getElementById("trackHourEnd").value;
	var trackMinuteEnd = document.getElementById("trackMinuteEnd").value;
	var trackEnd = (trackDateEnd + trackHourEnd + trackMinuteEnd);
	
	if(trackStart != "" && trackEnd==""){
		
		document.getElementById("trackDateStart").style.border = "";
		document.getElementById("trackHourStart").style.border = "";
		document.getElementById("trackMinuteStart").style.border = "";
		
		document.getElementById("trackDateEnd").style.border = "none";
		document.getElementById("trackHourEnd").style.border = "none";
		document.getElementById("trackMinuteEnd").style.border = "none";
		
		document.getElementById("errorTrack1").style.display = "none";
		document.getElementById("errorTrack2").style.display = "none";
		document.getElementById("errorTrack3").style.display = "none";
		
		document.getElementById("saveModal").style.display = "block";
		document.getElementById("saveTextModal2").style.display = "none";
		document.getElementById("SaveModalExit").style.display = "block";
		document.getElementById("saveModalVoltar").style.display = "none";
		document.getElementById("saveBtn").style.display = "block";
		
		document.getElementById("editModal").style.display = "block";
		document.getElementById("editorTextModal2").style.display = "none";
		document.getElementById("editModalExit").style.display = "block";
		document.getElementById("editModalVoltar").style.display = "none";
		document.getElementById("editarBtn").style.display = "block";
		
		comparationDate();
		
	}else if(trackStart <= trackEnd){
		
		document.getElementById("trackDateStart").style.border = "";
		document.getElementById("trackHourStart").style.border = "";
		document.getElementById("trackMinuteStart").style.border = "";
		
		document.getElementById("trackDateEnd").style.border = "";
		document.getElementById("trackHourEnd").style.border = "";
		document.getElementById("trackMinuteEnd").style.border = "";
		
		document.getElementById("errorTrack1").style.display = "none";
		document.getElementById("errorTrack2").style.display = "none";
		document.getElementById("errorTrack3").style.display = "none";
		
		document.getElementById("saveModal").style.display = "block";
		document.getElementById("saveTextModal2").style.display = "none";
		document.getElementById("SaveModalExit").style.display = "block";
		document.getElementById("saveModalVoltar").style.display = "none";
		document.getElementById("saveBtn").style.display = "block";
		
		document.getElementById("editModal").style.display = "block";
		document.getElementById("editorTextModal2").style.display = "none";
		document.getElementById("editModalExit").style.display = "block";
		document.getElementById("editModalVoltar").style.display = "none";
		document.getElementById("editarBtn").style.display = "block";
		
		comparationDate();
	
	}else if(trackStart > trackEnd){
	
		
		document.getElementById("trackDateStart").style.border = "";
		document.getElementById("trackHourStart").style.border = "";
		document.getElementById("trackMinuteStart").style.border = "";
		
		document.getElementById("errorTrack1").style.display = "block";
		document.getElementById("errorTrack2").style.display = "block";
		document.getElementById("errorTrack3").style.display = "block";
		
		document.getElementById("saveModal").style.display = "none";
		document.getElementById("saveTextModal2").style.display = "block";
		document.getElementById("SaveModalExit").style.display = "none";
		document.getElementById("saveModalVoltar").style.display = "block";
		document.getElementById("saveBtn").style.display = "none";
		
		document.getElementById("editModal").style.display = "none";
		document.getElementById("editorTextModal2").style.display = "block";
		document.getElementById("editModalExit").style.display = "none";
		document.getElementById("editModalVoltar").style.display = "block";
		document.getElementById("editarBtn").style.display = "none";

		return mostrarTab3();
	}
		
}
///////////////////////////////////////////////////////////

function ActionValidator(){
	
	//inicial
	var actionDateStart = document.getElementById("acaoData").value;
	var actionHourStart = document.getElementById("acaoInicio").value;
	var actionMinuteStart = document.getElementById("acaoDuracao").value;
	var actionStart = (actionDateStart + actionHourStart + actionMinuteStart);
	
	//final
	var actionDateEnd = document.getElementById("acaoDataFinal").value;
	var actionHourEnd = document.getElementById("acaoHoraFinal").value;
	var actionMinuteEnd = document.getElementById("acaoEndMinute").value;
	var actionEnd = (actionDateEnd + actionHourEnd + actionMinuteEnd);
	
	if(actionStart != "" && actionEnd==""){
		
		
		document.getElementById("acaoData").style.border = "";
		document.getElementById("acaoInicio").style.border = "";
		document.getElementById("acaoDuracao").style.border = "";
		
		document.getElementById("acaoDataFinal").style.border = "none";
		document.getElementById("acaoHoraFinal").style.border = "none";
		document.getElementById("acaoEndMinute").style.border = "none";
		
		document.getElementById("errorAction1").style.display = "none";
		document.getElementById("errorAction2").style.display = "none";
		document.getElementById("errorAction3").style.display = "none";
		
		document.getElementById("saveModal").style.display = "block";
		document.getElementById("saveTextModal3").style.display = "none";
		document.getElementById("SaveModalExit").style.display = "block";
		document.getElementById("saveModalVoltar").style.display = "none";
		document.getElementById("saveBtn").style.display = "block";
		
		document.getElementById("editModal").style.display = "block";
		document.getElementById("editorTextModal3").style.display = "none";
		document.getElementById("editModalExit").style.display = "block";
		document.getElementById("editModalVoltar").style.display = "none";
		document.getElementById("editarBtn").style.display = "block";
		
		comparationDate();
		
	}else if(actionStart <= actionEnd){
		
		
		document.getElementById("acaoData").style.border = "";
		document.getElementById("acaoInicio").style.border = "";
		document.getElementById("acaoDuracao").style.border = "";
		
		document.getElementById("acaoDataFinal").style.border = "";
		document.getElementById("acaoHoraFinal").style.border = "";
		document.getElementById("acaoEndMinute").style.border = "";
		
		document.getElementById("errorAction1").style.display = "none";
		document.getElementById("errorAction2").style.display = "none";
		document.getElementById("errorAction3").style.display = "none";
		
		document.getElementById("saveModal").style.display = "block";
		document.getElementById("saveTextModal3").style.display = "none";
		document.getElementById("SaveModalExit").style.display = "block";
		document.getElementById("saveModalVoltar").style.display = "none";
		document.getElementById("saveBtn").style.display = "block";
		
		document.getElementById("editModal").style.display = "block";
		document.getElementById("editorTextModal3").style.display = "none";
		document.getElementById("editModalExit").style.display = "block";
		document.getElementById("editModalVoltar").style.display = "none";
		document.getElementById("editarBtn").style.display = "block";
		
		comparationDate();
	
	}else if(actionStart > actionEnd){
		
		document.getElementById("acaoData").style.border = "solid red 2px";
		document.getElementById("acaoInicio").style.border = "solid red 2px";
		document.getElementById("acaoDuracao").style.border = "solid red 2px";
		
		document.getElementById("errorAction1").style.display = "block";
		document.getElementById("errorAction2").style.display = "block";
		document.getElementById("errorAction3").style.display = "block";
		
		document.getElementById("saveModal").style.display = "none";
		document.getElementById("saveTextModal3").style.display = "block";
		document.getElementById("SaveModalExit").style.display = "none";
		document.getElementById("saveModalVoltar").style.display = "block";
		document.getElementById("saveBtn").style.display = "none";
		
		document.getElementById("editModal").style.display = "none";
		document.getElementById("editorTextModal3").style.display = "block";
		document.getElementById("editModalExit").style.display = "none";
		document.getElementById("editModalVoltar").style.display = "block";
		document.getElementById("editarBtn").style.display = "none";
	
		return mostrarTab3();
	}
	
	return mostrarTab1();
}
//////////////////////////////////////////////////////////////////////////
function comparationDate(){
	
	//variaveis
	//EVENT
	var eventDateStart = document.getElementById("dataInicial").value;
	var eventHourStart = document.getElementById("horaInicial").value;
	var eventMinuteStart = document.getElementById("minutoInicial").value;
	var eventStart = (eventDateStart + eventHourStart + eventMinuteStart);
	//TRACK
	var trackDateStart = document.getElementById("trackDateStart").value;
	var trackHourStart = document.getElementById("trackHourStart").value;
	var trackMinuteStart = document.getElementById("trackMinuteStart").value;
	var trackStart = (trackDateStart + trackHourStart + trackMinuteStart);
	//ACTION
	var actionDateStart = document.getElementById("acaoData").value;
	var actionHourStart = document.getElementById("acaoInicio").value;
	var actionMinuteStart = document.getElementById("acaoDuracao").value;
	var actionStart = (actionDateStart + actionHourStart + actionMinuteStart);
	
	if((eventStart != "")&&(trackStart == "")&&(actionStart =="")){
		
		//inputs
		document.getElementById("dataInicial").style.border = "";
		document.getElementById("horaInicial").style.border = "";
		document.getElementById("minutoInicial").style.border = "";

		//mensagem e erro
		document.getElementById("errorEvent1").style.display = "none";
		document.getElementById("errorEvent2").style.display = "none";
		document.getElementById("errorEvent3").style.display = "none";

		//save
		document.getElementById("saveModal").style.display = "block";
		document.getElementById("saveTextModal4").style.display = "none";
		document.getElementById("SaveModalExit").style.display = "block";
		document.getElementById("saveModalVoltar").style.display = "none";
		document.getElementById("saveBtn").style.display = "block";
		//edit
		document.getElementById("editModal").style.display = "block";
		document.getElementById("editorTextModal4").style.display = "none";
		document.getElementById("editModalExit").style.display = "block";
		document.getElementById("editModalVoltar").style.display = "none";
		document.getElementById("editarBtn").style.display = "block";
		
	}else if((eventStart <= trackStart)||(eventStart <= actionStart)) {
		//inputs
		document.getElementById("dataInicial").style.border = "";
		document.getElementById("horaInicial").style.border = "";
		document.getElementById("minutoInicial").style.border = "";
		document.getElementById("trackDateStart").style.border = "";
		document.getElementById("trackHourStart").style.border = "";
		document.getElementById("trackMinuteStart").style.border = "";
		document.getElementById("acaoData").style.border = "";
		document.getElementById("acaoInicio").style.border = "";
		document.getElementById("acaoDuracao").style.border = "";
		//mensagem e erro
		document.getElementById("errorEvent1").style.display = "none";
		document.getElementById("errorEvent2").style.display = "none";
		document.getElementById("errorEvent3").style.display = "none";
		document.getElementById("errorTrack1").style.display = "none";
		document.getElementById("errorTrack2").style.display = "none";
		document.getElementById("errorTrack3").style.display = "none";
		document.getElementById("errorAction1").style.display = "none";
		document.getElementById("errorAction2").style.display = "none";
		document.getElementById("errorAction3").style.display = "none";

		//save
		document.getElementById("saveModal").style.display = "block";
		document.getElementById("saveTextModal4").style.display = "none";
		document.getElementById("SaveModalExit").style.display = "block";
		document.getElementById("saveModalVoltar").style.display = "none";
		document.getElementById("saveBtn").style.display = "block";
		//edit
		document.getElementById("editModal").style.display = "block";
		document.getElementById("editorTextModal4").style.display = "none";
		document.getElementById("editModalExit").style.display = "block";
		document.getElementById("editModalVoltar").style.display = "none";
		document.getElementById("editarBtn").style.display = "block";
		
		if((eventStart !="")&&(actionStart =="")){
			
		//inputs
		document.getElementById("dataInicial").style.border = "";
		document.getElementById("horaInicial").style.border = "";
		document.getElementById("minutoInicial").style.border = "";
		//mensagem e erro
		document.getElementById("errorEvent1").style.display = "none";
		document.getElementById("errorEvent2").style.display = "none";
		document.getElementById("errorEvent3").style.display = "none";
		document.getElementById("errorTrack1").style.display = "none";
		document.getElementById("errorTrack2").style.display = "none";
		document.getElementById("errorTrack3").style.display = "none";

		//save
		document.getElementById("saveModal").style.display = "block";
		document.getElementById("saveTextModal4").style.display = "none";
		document.getElementById("SaveModalExit").style.display = "block";
		document.getElementById("saveModalVoltar").style.display = "none";
		document.getElementById("saveBtn").style.display = "block";
		//edit
		document.getElementById("editModal").style.display = "block";
		document.getElementById("editorTextModal4").style.display = "none";
		document.getElementById("editModalExit").style.display = "block";
		document.getElementById("editModalVoltar").style.display = "none";
		document.getElementById("editarBtn").style.display = "block";
		
		}else if((eventStart > actionStart)){
			
		//inputs
		document.getElementById("dataInicial").style.border = "solid red 2px";
		document.getElementById("horaInicial").style.border = "solid red 2px";
		document.getElementById("minutoInicial").style.border = "solid red 2px";
		document.getElementById("acaoData").style.border = "solid red 2px";
		document.getElementById("acaoInicio").style.border = "solid red 2px";
		document.getElementById("acaoDuracao").style.border = "solid red 2px";
		//mensagem e erro
		document.getElementById("errorEvent1").style.display = "block";
		document.getElementById("errorEvent2").style.display = "block";
		document.getElementById("errorEvent3").style.display = "block";

		document.getElementById("errorAction1").style.display = "block";
		document.getElementById("errorAction2").style.display = "block";
		document.getElementById("errorAction3").style.display = "block";

		//save
		document.getElementById("saveModal").style.display = "none";
		document.getElementById("saveTextModal4").style.display = "block";
		document.getElementById("SaveModalExit").style.display = "none";
		document.getElementById("saveModalVoltar").style.display = "block";
		document.getElementById("saveBtn").style.display = "none";
		//edit
		document.getElementById("editModal").style.display = "none";
		document.getElementById("editorTextModal4").style.display = "block";
		document.getElementById("editModalExit").style.display = "none";
		document.getElementById("editModalVoltar").style.display = "block";
		document.getElementById("editarBtn").style.display = "none";
			
		}
		
	}else if((eventStart > trackStart)){
		
		//inputs
		document.getElementById("dataInicial").style.border = "solid red 2px";
		document.getElementById("horaInicial").style.border = "solid red 2px";
		document.getElementById("minutoInicial").style.border = "solid red 2px";
		document.getElementById("trackDateStart").style.border = "solid red 2px";
		document.getElementById("trackHourStart").style.border = "solid red 2px";
		document.getElementById("trackMinuteStart").style.border = "solid red 2px";

		//mensagem e erro
		document.getElementById("errorEvent1").style.display = "block";
		document.getElementById("errorEvent2").style.display = "block";
		document.getElementById("errorEvent3").style.display = "block";
		document.getElementById("errorTrack1").style.display = "block";
		document.getElementById("errorTrack2").style.display = "block";
		document.getElementById("errorTrack3").style.display = "block";

		//save
		document.getElementById("saveModal").style.display = "none";
		document.getElementById("saveTextModal4").style.display = "block";
		document.getElementById("SaveModalExit").style.display = "none";
		document.getElementById("saveModalVoltar").style.display = "block";
		document.getElementById("saveBtn").style.display = "none";
		//edit
		document.getElementById("editModal").style.display = "none";
		document.getElementById("editorTextModal4").style.display = "block";
		document.getElementById("editModalExit").style.display = "none";
		document.getElementById("editModalVoltar").style.display = "block";
		document.getElementById("editarBtn").style.display = "none";
		
	}
	
}
////////////////////////////////////////////////////////
 //////////////////////////////////////////////////////
/////ANIMAÇÃO ABERTURA DO SITE////////////////////////
	$(window).on('load', function () {
        $('#preloader .inner').fadeOut();
        $('#preloader').delay(350).fadeOut('slow'); 
        
    });
 ////////////////////////////////////////////////////////////
////BTN ADD FILE///AND///HIDDEN BTN ADD FILE///LIST FILE/////
function alterarBtn(){
	document.getElementById("save").style.display = "none";
	document.getElementById("alterar").style.display = "block";
	document.getElementById("new").disabled = true;
	document.getElementById("listDateFile").style.display = "block";
	//document.getElementById("").disabled = true;
	
	//alert("estou aqui");
	valueOcc();
	listingFile();
}
function valueOcc(){
	var value = document.getElementById("occNumber").value;
	document.getElementById("occValue").value = value;
	document.getElementById("selectedId").value = value;
	//alert(value)
}
///////ADD FILE//////////////////////////////////////////////////
function myFunction(){
	$(document).ready(function() {
		if (window.File && window.FileList && window.FileReader) {
			$("#myFile").on("change", function(e) {
				var files = e.target.files,
				filesLength = files.length;
				for (var i = 0; i < filesLength; i++) {
					var f = files[i]
					var fileReader = new FileReader();
					fileReader.onload = (function(e) {
						var file = e.target;
						$("<span class=\"pip\">" +
						"<img class=\"imageThumb\" src=\"" + e.target.result + "\" title=\"" + file.name + "\"/>" +
						"<br/><span class=\"remove\"><i class=\"fas fa-times\"></i></span>" +
						"</span>").insertAfter("#myFile");
						$(".remove").click(function(){
							$(this).parent(".pip").remove();
						});
					});
					fileReader.readAsDataURL(f);
				}
			});
		} else{
			alert("Your browser doesn't support to File API")
		}
	});	
}
function myFunction2(){
	$(document).ready(function() {
		if (window.File && window.FileList && window.FileReader) {
			$("#myFile2").on("change", function(e) {
				var files = e.target.files,
				filesLength = files.length;
				for (var i = 0; i < filesLength; i++) {
					var f = files[i]
					var fileReader = new FileReader();
					fileReader.onload = (function(e) {
						var file = e.target;
						$("<span class=\"pip\">" +
						"<img class=\"imageThumb\" src=\"" + e.target.result + "\" title=\"" + file.name + "\"/>" +
						"<br/><span class=\"remove\"><i class=\"fas fa-times\"></i></span>" +
						"</span>").insertAfter("#myFile2");
						$(".remove").click(function(){
							$(this).parent(".pip").remove();
						});
					});
					fileReader.readAsDataURL(f);
				}
			});
		} else{
			alert("Your browser doesn't support to File API")
		}
	});	
}
 ////STYLE CSS3 BTN/////////////////////////////////////////////

 /////////////////////////////////////////////////////////////////////////////////////
/*BTN DESCRIPTION ACTION - OCULTA BTN E TEXTEAREA*///////////////////////////////////
function hiddenActionDescription1(){                                             ///            ///
	document.getElementById("acaoDescr1").style.display = "none";            ///           ///
	document.getElementById("acaoDescrOtro1").style.display = "block";       ///
	
	document.getElementById("actionDescr2").style.background = "blue";
	document.getElementById("actionDescr1").style.background = "black"																        ///		
}																	       ///		
/*BTN DESCRIPTION ACTION - INVERTE BTN E TEXTEAREA*//////////////////////////
function hiddenActionDescription2(){									    
	document.getElementById("acaoDescrOtro1").style.display = "none";
	document.getElementById("acaoDescr1").style.display = "block";
	
	document.getElementById("actionDescr1").style.background = "blue";
	document.getElementById("actionDescr2").style.background = "black"																 //			
}
/*BTN DESCRIPTION ACTION - OCULTA BTN E TEXTEAREA*///////////////////////////////////
function hiddenActionDescription3(){                                             ///            ///
	document.getElementById("damageBtn1").style.display = "block";            ///           ///
	document.getElementById("damageBtn2").style.display = "none"; 
	
	document.getElementById("actionDescr3").style.background = "blue";
	document.getElementById("actionDescr4").style.background = "black"      ///
																	        ///		
}																	       ///		
/*BTN DESCRIPTION ACTION - INVERTE BTN E TEXTEAREA*//////////////////////////
function hiddenActionDescription4(){									    
	document.getElementById("damageBtn1").style.display = "none";
	document.getElementById("damageBtn2").style.display = "block";
	
	document.getElementById("actionDescr4").style.background = "blue";
	document.getElementById("actionDescr3").style.background = "black"
															 //			
}/*BTN DESCRIPTION ACTION - OCULTA BTN E TEXTEAREA*///////////////////////////////////
function hiddenActionDescription5(){                                             ///            ///
	document.getElementById("description1").style.display = "block";            ///           ///
	document.getElementById("description2").style.display = "none";  
	
	document.getElementById("descriptionBtn1").style.background = "blue";
	document.getElementById("descriptionBtn2").style.background = "black";     ///
																	        ///		
}																	       ///		
/*BTN DESCRIPTION ACTION - INVERTE BTN E TEXTEAREA*//////////////////////////
function hiddenActionDescription6(){									    
	document.getElementById("description1").style.display = "none";
	document.getElementById("description2").style.display = "block";
	
	document.getElementById("descriptionBtn2").style.background = "blue";
	document.getElementById("descriptionBtn1").style.background = "black";														 //			
}
/*BTN DESCRIPTION ACTION - OCULTA BTN E TEXTEAREA*/////////////////////////////
function hiddenActionDescription7(){                                           
	document.getElementById("involvedDescription1").style.display = "block";           
	document.getElementById("involvedDescription2").style.display = "none"; 
	
	document.getElementById("involvedBtn1").style.background = "blue";
	document.getElementById("involvedBtn2").style.background = "black";																        ///		
}																	       	
/*BTN DESCRIPTION ACTION - INVERTE BTN E TEXTEAREA*//////////////////////////
function hiddenActionDescription8(){									    
	document.getElementById("involvedDescription1").style.display = "none";
	document.getElementById("involvedDescription2").style.display = "block";
	
	document.getElementById("involvedBtn2").style.background = "blue";
	document.getElementById("involvedBtn1").style.background = "black";	
													
}	
function hiddenActionDescription9(){                                                     
	document.getElementById("descriptionCause1").style.display = "block";            
	document.getElementById("descriptionCause2").style.display = "none";
	
	document.getElementById("causeProvavel1").style.background = "blue";
	document.getElementById("causeProvavel2").style.background = "black";
	
																        ///		
}																	       ///		
/*BTN DESCRIPTION ACTION - INVERTE BTN E TEXTEAREA*//////////////////////////
function hiddenActionDescription10(){									    
	document.getElementById("descriptionCause1").style.display = "none";
	document.getElementById("descriptionCause2").style.display = "block";
	
	document.getElementById("causeProvavel2").style.background = "blue";
	document.getElementById("causeProvavel1").style.background = "black";
															 //			
}																		//
/////////////////////////////////////////////////////////////////////
function validatorFile(){
	
	var file = document.getElementById("myFile").value;
	
	if(file == ""){
		
		document.getElementById("myFile").style.border = "solid 2px red";
		document.getElementById("errorFile").style.display = "block";
		//alert("estamos aqui");
		//cancela a ação do submit
		$("#form-cadastro").submit(function(event){
  			event.preventDefault();
		});
		
	}else if(file != ""){
		
		//exclui o evento anterior aplicado
		$("#form-cadastro").unbind();
		
		document.getElementById("myFile").style.border = "";
		document.getElementById("errorFile").style.display = "none";
		
		return mostrarTab2();
	}

}
function validatorFile2(){
	
	var file = document.getElementById("myFile2").value;
	
	if(file == ""){
		
		document.getElementById("myFile2").style.border = "solid 2px red";
		document.getElementById("errorFile2").style.display = "block";
		//alert("estamos aqui");
		//cancela a ação do submit
		$("#form-cadastro").submit(function(event){
  			event.preventDefault();
		});
		
	}else if(file != ""){
		
		//exclui o evento anterior aplicado
		$("#form-cadastro").unbind();
		
		document.getElementById("myFile2").style.border = "";
		document.getElementById("errorFile2").style.display = "none";
		
		return mostrarTab2();
	}
}

setTimeout(function() {
   $('#msgSave1').fadeOut('fast');
},3000);
setTimeout(function() {
   $('#msgDelete2').fadeOut('fast');
}, 3000);
setTimeout(function() {
   $('#msgDownload3').fadeOut('fast');
}, 3000);
setTimeout(function() {
   $('#msgFinished').fadeOut('fast');
}, 3000);
function msgDelete(){
	//document.getElementById("msgDelete").style.display = "block";
	document.getElementById("msgDelete2").style.display = "block";
}
function msgDownload(){
	//document.getElementById("msgDownload").style.display = "block";
	document.getElementById("msgDownload3").style.display = "block";
}
function msgSaveFile(){
	//document.getElementById("msgSave").style.display = "block";
	document.getElementById("msgSave1").style.display = "block";
}
function msgFinished(){
	document.getElementById("msgFinished").style.display = "block";
}
function msgFinishedHidden(){
	document.getElementById("msgFinished").style.display = "none";
}
//mascara dos input KM
 $(document).ready(function () { 
        var $km = $("#eventoKm");
        $km.mask('000+000', {reverse: false});
    });
$(document).ready(function () { 
        var $km = $("#trasitoExtKm");
        $km.mask('000+000', {reverse: false});
    });


function listingFileBtnHidden(){
	//document.getElementById("btnListingFile").style.display = "none";
	//document.getElementById("tableListingFile").style.display = "none";
}
function listingFile(){
	document.getElementById("tableListingFile").style.display = "none";
}
function listingFile1(){
	document.getElementById("tableListingFile").style.display = "block";
}
function disableEdit(){
	document.getElementById("edit").disabled = true;
}
function hiddenBtnIcon(){
	document.getElementById("updateDeleteIcon").style.display = "none";
	document.getElementById("updateDownloadIcon").style.display = "none";

}
function btnIconBlock(){
	document.getElementById("updateDeleteIcon").style.display = "block";
	document.getElementById("updateDownloadIcon").style.display = "block";

}
function fileTotal(){
	document.getElementById("fileTotal").style.display = "block";
}
function fileTotalHidden(){
	document.getElementById("fileTotal").style.display = "none";
}
function listUpdateFile1(){
	document.getElementById("listUpdateFileT").style.display = "none"
	document.getElementById("listDateFile").style.display = "block";

}
function listUpdateFile2(){
	document.getElementById("listUpdateFileT").style.display = "block"
}
function alterBtnReset(){
	document.getElementById("resetUpdate").style.display = "block";
	document.getElementById("anularAtual").style.display = "none";
}
function uploadFile(){
	document.getElementById("uploadFile1").style.display = "block";
	document.getElementById("listDateFile").style.display = "none";
}
function hiddenSave(){
	var save = 	document.getElementById("save").style.display = "none";

}
