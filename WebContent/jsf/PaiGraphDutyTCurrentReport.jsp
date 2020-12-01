<ui:composition  xmlns="http://www.w3.org/1999/xhtml"
      xmlns:f="http://java.sun.com/jsf/core"
      xmlns:ui="http://java.sun.com/jsf/facelets"
      xmlns:h="http://java.sun.com/jsf/html"
      xmlns:a4j="http://richfaces.org/a4j"
	  xmlns:rich="http://richfaces.org/rich">

 

<head>



    <!--Load the AJAX API-->
     <script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
  <script type="text/javascript" src="https://www.upexciseonline.in/doc/ExciseUp/graphjs/graphPaiDutyTCurrent.js"></script>
  </head>




<f:view>
    <!--Div that will hold the pie chart-->
    <h:form>
    <div><rich:spacer height="30px"></rich:spacer></div>
  <div align="center"><h:commandButton value="Back"
					action="#{barGraphAction.back}"
					style="display: inline-block; background-color: #4CAF50;"></h:commandButton> </div>
					</h:form>
  <div class="row" align="center">
		 
						<div class="col-md-2" align="center"></div>
						
		<div class="col-md-4"  id="BarChartt1" style="border: 1px solid #ccc"></div>
		<div class="col-md-1" align="center"></div>
        <div class="col-md-4"  id="BarChartt2" style="border: 1px solid #ccc"></div>
      </div>
     
     
      
      
      <div><rich:spacer height="30px"></rich:spacer>
      </div>
      
       <div class="row" align="center">
       <div class="col-md-2" align="center"></div>
						
		<div class="col-md-4"  id="BarChartt3" style="border: 1px solid #ccc"></div>
		<div class="col-md-1" align="center"></div>
        <div class="col-md-4"  id="BarChartt4" style="border: 1px solid #ccc"></div>
      </div>
      
     
       <div class="row" align="center">
       <div class="col-md-2" align="center"></div>
						
		<div class="col-md-4"  id="BarChartt5" style="border: 1px solid #ccc"></div>
		<div class="col-md-1" align="center"></div>
        <div class="col-md-4"  id="BarChartt6" style="border: 1px solid #ccc"></div>
      </div>
      
      
  
</f:view>

</ui:composition>