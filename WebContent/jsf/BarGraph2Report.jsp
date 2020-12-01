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
  <script type="text/javascript" src="https://www.upexciseonline.in/doc/ExciseUp/graphjs/graphMonth.js"></script>
  </head>




<f:view>
    <!--Div that will hold the pie chart-->
    <h:form>
    <h:inputHidden value="#{barGraphAction.hidden}" />
    <div align="center"><a target="_self" href="/portal/home" align="center" id="h9" >  <h:outputText value="Back" />
		 </a><rich:spacer height="30px"></rich:spacer></div>
  <div class="row" align="center">
  <div class="col-md-6" align="right">
  
   <h:commandButton value="Today"
					action="#{barGraphAction.currentDay}"
					></h:commandButton>
  
  <h:commandButton value="Yesterday"
					action="#{barGraphAction.today}"
					styleClass="btn btn-success btn-sm"
					style="background:#e6e6e6; border-color:#e6e6e6;color:black;"></h:commandButton>
					
					<h:commandButton value="Month"
					action="#{barGraphAction.month}"
					style="display: inline-block; background-color: #4CAF50;
 					box-shadow: 0 12px 16px 0 rgba(0,0,0,0.24), 0 17px 50px 0 rgba(0,0,0,0.19);" disabled="true"></h:commandButton>
					
					
					
					
					
					</div>
					<div class="col-md-6" align="left"> <h:selectOneMenu  onchange="this.form.submit();"
			styleClass="dropdown-menu"  value="#{barGraphAction.selectMonth}"
			valueChangeListener="#{barGraphAction.chngval}">
			<f:selectItem itemValue="K" itemLabel="--Select Month--" /> 
           
            <f:selectItem itemValue="3" itemLabel="April" />
            <f:selectItem itemValue="4" itemLabel="May" /> 
            <f:selectItem itemValue="5" itemLabel="June" />	
            <f:selectItem itemValue="6" itemLabel="July" />	  
            <f:selectItem itemValue="7" itemLabel="August" />
            <f:selectItem itemValue="8" itemLabel="September"/>
            <f:selectItem itemValue="9" itemLabel="October" />
            <f:selectItem itemValue="10" itemLabel="November "/>
            <f:selectItem itemValue="11" itemLabel="December" />
             <f:selectItem itemValue="0" itemLabel="January" />	
            <f:selectItem itemValue="1" itemLabel="February" />	  
            <f:selectItem itemValue="2" itemLabel="March" />	 
   			</h:selectOneMenu><h:commandButton value="Year 2020-21"
					action="#{barGraphAction.total}"
					styleClass="btn btn-success btn-sm"
					style="background:#e6e6e6; border-color:#e6e6e6;color:black;"></h:commandButton> </div>
					
					</div>
					
					
   			
					</h:form>
	
					
					
	
  <div class="row" align="center">
		 
						<div class="col-md-2" align="center"></div>
						
		<div class="col-md-4"  id="BarChart1" style="border: 1px solid #ccc"></div>
		<div class="col-md-1" align="center"></div>
        <div class="col-md-4"  id="BarChart2" style="border: 1px solid #ccc"></div>
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
						
		<div class="col-md-4"  id="BarChart3" style="border: 1px solid #ccc"></div>
		<div class="col-md-1" align="center"></div>
        <div class="col-md-4"  id="BarChart4" style="border: 1px solid #ccc"></div>
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
       
						
		<div id="BarChart5" style="border: 1px solid #ccc"></div>
		
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
       
						
		<div id="BarChart6" style="border: 1px solid #ccc"></div>
		
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