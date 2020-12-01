<ui:composition xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:a4j="http://richfaces.org/a4j"
	xmlns:rich="http://richfaces.org/rich">
	
<div class="panel panel-default" style="background-color: #F0FFFF;">
	<h:form>
		<f:view>
			<head>
						<style>
						.heading {
						      margin:40px;
						      
							
						}
						.buttonprint{
						border-radius : 20px;
						
						}
						.dropdown-menu {
							border-radius: 6px;
							padding: 5px 5px;
							height: 30px;
							width: 30%;
							box-shadow: 1px 1px 15px lightsteelblue;
							border: 1px solid #669999;
						}
						.buttonprint:hover{
						font-size:	large;				
						
						}
						
						</style>
			</head>
								

			

				<div align="center" class="heading">
					<h:inputHidden value="#{stock_FL3_FL3A_action.hidden}" />
					<h:outputText value="Stock At FL3/FL3A/CL 2020-2021"
						style="FONT-FAMILY: 'Eras Medium ITC'; font-size: 40px;font-style: italic bold;color:black;"></h:outputText>
					<rich:spacer height="10px"></rich:spacer>

				</div>
				<rich:spacer height="10px"></rich:spacer>

				
					<div class="row" align="center">
						<h:selectOneRadio value="#{stock_FL3_FL3A_action.vch_from}"
						style="FONT-SIZE:small; FONT-WEIGHT: bold;FONT-FAMILY: 'Yu Gothic Medium';"
							onchange="this.form.submit()"
							valueChangeListener="#{stock_FL3_FL3A_action.fromListMethod}">
							<f:selectItem itemValue="FL3" itemLabel="FL3" />
							<rich:spacer width="5px"></rich:spacer>
							<f:selectItem itemValue="FL3A" itemLabel="FL3A" />
							<rich:spacer width="5px"></rich:spacer>
							<f:selectItem itemValue="CL" itemLabel="CL" />

						</h:selectOneRadio>
					</div>

					<rich:spacer height="20px"></rich:spacer>
									
				<div  align="center">
					<h:outputText value="Licence No."
					rendered="#{stock_FL3_FL3A_action.vch_from ne 'CL'}"
						style="FONT-SIZE:small; FONT-WEIGHT: bold;FONT-FAMILY: 'Yu Gothic Medium';"></h:outputText>
					
					<h:selectOneMenu
						value="#{stock_FL3_FL3A_action.lic_id}" 
						rendered="#{stock_FL3_FL3A_action.vch_from ne 'CL'}"
						style="height: 28px; width : 300px;COLOR: #FF1493;">
						<f:selectItems value="#{stock_FL3_FL3A_action.getAll_List}" />
					</h:selectOneMenu>
				</div>						
							

					<div class="row " align="center">
						<rich:spacer height="10px"></rich:spacer>
					</div>


					<div class="col-md-3"></div>
				
				<div class="row " align="center">
					<rich:spacer height="30px"></rich:spacer>
				</div>



				<div class="row">
					<div align="center" class="buttonprint">

						<h:commandButton action="#{stock_FL3_FL3A_action.print}"
							styleClass="btn btn-success" value="Print Report"
							style="FONT-SIZE: medium; border-radius: 10px;" />

						<h:outputLink styleClass="outputLinkEx"
							value="/doc/ExciseUp/WholeSale/pdf//#{stock_FL3_FL3A_action.pdfname}"
							target="_blank">
							<h:outputText styleClass="outputText" id="text223"
								value="View Report"
								rendered="#{stock_FL3_FL3A_action.printFlag==true}"
								style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
						</h:outputLink>
						
						<h:commandButton action="#{stock_FL3_FL3A_action.excel}"
						value="Generate Excel" styleClass="btn btn-success" />
						
					<h:outputLink styleClass="outputLinkEx"
						value="/doc/ExciseUp//MIS//Excel//#{stock_FL3_FL3A_action.exlname}"
						target="_blank">
						
						<h:outputText styleClass="outputText" value="Download Excel" 
							rendered="#{stock_FL3_FL3A_action.excelFlag==true}"
							style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
					</h:outputLink>
						<h:commandButton value="Reset"
							action="#{stock_FL3_FL3A_action.reset}"
							style="font-size: medium;border-radius: 10px; "
							styleClass="btn btn-danger"></h:commandButton>
					</div>
				</div>
				<rich:spacer height="40px"></rich:spacer>
		</f:view>
	</h:form>
	 </div>
</ui:composition>