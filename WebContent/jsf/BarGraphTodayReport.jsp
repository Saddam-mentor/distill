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
  <script type="text/javascript" src="https://www.upexciseonline.in/doc/ExciseUp/graphjs/currentDay.js"></script>
  </head>




<f:view>
    <!--Div that will hold the pie chart-->
    <h:form>
    <div align="center"><a target="_self" href="/portal/home" align="center" id="h9" >  <h:outputText value="Back" />
		 </a><rich:spacer height="30px"></rich:spacer></div>
  <div align="center">
  <h:commandButton
  style="display: inline-block; background-color: #4CAF50;
 					box-shadow: 0 12px 16px 0 rgba(0,0,0,0.24), 0 17px 50px 0 rgba(0,0,0,0.19);"
  value="Today" disabled="true"
  ></h:commandButton>
  
  
  
  
  <h:commandButton value="Yesterday"
					action="#{barGraphAction.currentDay}"
					></h:commandButton>
					<h:commandButton value="Month"
					action="#{barGraphAction.month}"
					styleClass="btn btn-success btn-sm"
					style="background:#e6e6e6; border-color:#e6e6e6;color:black;"></h:commandButton>
					<h:commandButton value="Year"
					action="#{barGraphAction.total}"
					styleClass="btn btn-success btn-sm"
					style="background:#e6e6e6; border-color:#e6e6e6;color:black;"></h:commandButton></div>
					</h:form>
  <div class="row" align="center">
		 
						<div class="col-md-2" align="center"></div>
						
		<div class="col-md-4"  id="BarChartt1" style="border: 1px solid #ccc"></div>
		<div class="col-md-1" align="center"></div>
        <div class="col-md-4"  id="BarChartt2" style="border: 1px solid #ccc"></div>
      </div>
      
      <h:form>
  <div class="row" align="center">
		 
						<div class="col-md-2" align="center"></div>
						
		<div class="col-md-4" >
		
		<h:commandButton value="See Detail" 
					action="#{barGraphAction.seedetail}"
					style="display: inline-block; background-color: #4CAF50;
 					box-shadow: 0 12px 16px 0 rgba(0,0,0,0.24), 0 17px 50px 0 rgba(0,0,0,0.19);"></h:commandButton></div>
		<div class="col-md-1" align="center">
		
		</div>
        <div class="col-md-4" >
        
		<h:commandButton value="See Detail" 
					action="#{barGraphAction.seedetail1}"
					style="display: inline-block; background-color: #4CAF50;
 					box-shadow: 0 12px 16px 0 rgba(0,0,0,0.24), 0 17px 50px 0 rgba(0,0,0,0.19);" ></h:commandButton>
        </div> </div>
        </h:form>
      
      <div><rich:spacer height="30px"></rich:spacer>
      </div>
      
       <div class="row" align="center">
       <div class="col-md-2" align="center"></div>
						
		<div class="col-md-4"  id="BarChartt3" style="border: 1px solid #ccc"></div>
		<div class="col-md-1" align="center"></div>
        <div class="col-md-4"  id="BarChartt4" style="border: 1px solid #ccc"></div>
      </div>
      
       <h:form>
  <div class="row" align="center">
		 
						<div class="col-md-2" align="center"></div>
						
		<div class="col-md-4" >
		
		<h:commandButton value="See Detail" 
					action="#{barGraphAction.seedetail2}"
					style="display: inline-block; background-color: #4CAF50;
 					box-shadow: 0 12px 16px 0 rgba(0,0,0,0.24), 0 17px 50px 0 rgba(0,0,0,0.19);"></h:commandButton></div>
		<div class="col-md-1" align="center">
		
		</div>
        <div class="col-md-4" >
        
		<h:commandButton value="See Detail" 
					action="#{barGraphAction.seedetail3}"
					style="display: inline-block; background-color: #4CAF50;
 					box-shadow: 0 12px 16px 0 rgba(0,0,0,0.24), 0 17px 50px 0 rgba(0,0,0,0.19);" ></h:commandButton>
        </div> </div>
        </h:form>
     
      
       <div align="center">
       
						
		<div id="BarChartt5" style="border: 1px solid #ccc"></div>
		
      </div>
      
     <h:form>
  <div  align="center">
		 
		<h:commandButton value="See Detail" 
					action="#{barGraphAction.seedetail4}"
					style="display: inline-block; background-color: #4CAF50;
 					box-shadow: 0 12px 16px 0 rgba(0,0,0,0.24), 0 17px 50px 0 rgba(0,0,0,0.19);" ></h:commandButton>
        </div> 
        </h:form>  
   <div align="center">
       
						
		<div id="BarChartt6" style="border: 1px solid #ccc"></div>
		
      </div>
      
      <h:form>
  <div  align="center">
		 
		<h:commandButton value="See Detail" 
					action="#{barGraphAction.seedetail5}"
					style="display: inline-block; background-color: #4CAF50;
 					box-shadow: 0 12px 16px 0 rgba(0,0,0,0.24), 0 17px 50px 0 rgba(0,0,0,0.19);" ></h:commandButton>
        </div> 
        </h:form>
  
</f:view>

</ui:composition>