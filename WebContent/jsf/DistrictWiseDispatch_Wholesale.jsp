<ui:composition xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:a4j="http://richfaces.org/a4j"
	xmlns:rich="http://richfaces.org/rich">

 <f:view>
		<h:form style="background-color:whitesmoke;">

			<div class="form-group">
				<div class="row">
					<a4j:outputPanel id="msg">
						<div class="row col-md-12 wow shake" style="margin-top: 10px;">
							<h:messages errorStyle="color:red"
								style="font-size: 14px;font-weight: bold"
								styleClass="generalExciseStyle" layout="table"
								infoStyle="color:green" />
						</div>
					</a4j:outputPanel>
				</div>

				<hr style="border-top: 7px #D0D3D4; border-top-style: dashed;"></hr>
				<div class="row">
					<div align="center">
						<h:outputText value="District Wise Dispatch Wholesale"
							style="COLOR: #0000a0; FONT-WEIGHT: bold; FONT-SIZE: 35px;font-family:Comic Sans MS;"></h:outputText>
					</div>
				</div>
				<hr style="border-top: 7px #D0D3D4; border-top-style: dashed;"></hr>
				<div>
					
				</div>
				<div class="row " align="center">
					<rich:spacer height="10px"></rich:spacer>
				</div>

				<div class="row col-md-12" align="center"
					style="BACKGROUND-COLOR: #c3e1f4;">
					<div class="col-md-12" align="center">
						<h:selectOneRadio
							style="FONT-WEIGHT: bold; "
							value="#{districtWiseDispatch_WholesaleAction.radioType}"
							onchange="this.form.submit();">
						
							<f:selectItem itemValue="CL" itemLabel="CL" />
							<f:selectItem itemValue="FL" itemLabel="FL" />
							<f:selectItem itemValue="FL2B" itemLabel="BEER" />
						</h:selectOneRadio>
					</div>
				</div>

				<div class="row">
					<rich:spacer height="30px"></rich:spacer>
				</div>

				<div class="row col-md-12" align="center"
					style="FONT-STYLE: italic; FONT-WEIGHT: bold;">
					Select Up To:
					<rich:calendar value="#{districtWiseDispatch_WholesaleAction.production_dt}"></rich:calendar>
				
				</div>		
           <rich:spacer height="20px"></rich:spacer>
<div class="col-md-12" align="center">


						<h:commandButton 
							action="#{districtWiseDispatch_WholesaleAction.excel}"
							value="Generate Excel"
							style="background-color:#C5C5C5; font-size: large;"
							 />

						<h:outputLink styleClass="outputLinkEx"
							value="/doc/ExciseUp//MIS//Excel//#{districtWiseDispatch_WholesaleAction.exlname}"
							target="_blank">
							<h:outputText styleClass="outputText" value="Download Excel"
								rendered="#{districtWiseDispatch_WholesaleAction.excelFlag==true}"
								style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
						</h:outputLink>

<h:commandButton 
							action="#{districtWiseDispatch_WholesaleAction.reset}"
							value="Reset"
							style="background-color:#C5C5C5; font-size: large;"
							 />

					</div>
				<br /> <br />
				
				
				</div>
				</h:form>
				</f:view>
</ui:composition>