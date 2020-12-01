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
						<h:outputText value="Import Permit Status"
							style="COLOR: #0000a0; FONT-WEIGHT: bold; FONT-SIZE: 35px; font-family:Comic Sans MS;"></h:outputText>
					</div>
				</div>
				<hr style="border-top: 7px #D0D3D4; border-top-style: dashed;"></hr>
				<div class="row " align="center">
					<rich:spacer height="10px"></rich:spacer>
				</div>

				<div class="row col-md-12" align="center">
					<h:outputText value="Select Type: "
						style="FONT-SIZE:small; FONT-WEIGHT: bold;"></h:outputText>

					<h:selectOneMenu value="#{importPermitStatusAction.unitType}"
						style="height: 25px; width : 150px;"
						valueChangeListener="#{importPermitStatusAction.typeListener}"
						onchange="this.form.submit();">
						<f:selectItem itemValue="Select" itemLabel="--Select--" />
						<f:selectItem itemValue="1" itemLabel="BWFL2A" />
						<f:selectItem itemValue="2" itemLabel="BWFL2B" />
						<f:selectItem itemValue="3" itemLabel="BWFL2C" />
						<f:selectItem itemValue="4" itemLabel="BWFL2D" />
						<f:selectItem itemValue="99" itemLabel="FL2D" />
					</h:selectOneMenu>
				</div>
				<div class="row " align="center">
					<rich:spacer height="20px"></rich:spacer>
				</div>
				<div class="row col-md-12" align="center"
					style="BACKGROUND-COLOR: #dee0e2;">
					<div class="col-md-12" align="center">
						<h:selectOneRadio
							style="FONT-WEIGHT: bold;  font-size: 17px; width: 20%; "
							value="#{importPermitStatusAction.radioType}"
							valueChangeListener="#{importPermitStatusAction.radioListener}"
							onchange="this.form.submit();">
							<f:selectItem itemValue="CD" itemLabel="Consolidated" />
							<f:selectItem itemValue="DT" itemLabel="Detail" />							
						</h:selectOneRadio>
					</div>
				</div>
				<div class="row " align="center">
					<rich:spacer height="30px"></rich:spacer>
				</div>
				<br />
				<div class="row" align="center">
					<h:commandButton value="Print Report" styleClass="btn btn-success"
						action="#{importPermitStatusAction.print}">
					</h:commandButton>

					<h:outputLink styleClass="outputLinkEx"
						value="/doc/ExciseUp/MIS/pdf/#{importPermitStatusAction.pdfName}"
						target="_blank">
						<h:outputText styleClass="outputText" id="text223"
							value="View Report"
							rendered="#{importPermitStatusAction.printFlag==true}"
							style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
					</h:outputLink>

					<rich:spacer width="10px;"></rich:spacer>
					<h:commandButton value="Reset" styleClass="btn btn-warning"
						action="#{importPermitStatusAction.reset}">
					</h:commandButton>
					
				</div>

<div class="col-md-12" align="center">


					<h:commandButton action="#{importPermitStatusAction.excel}"
						value="Generate Excel" styleClass="btn btn-success" />
					<h:outputLink styleClass="outputLinkEx"
						value="/doc/ExciseUp//MIS//Excel//#{importPermitStatusAction.exlname}"
						target="_blank">
						<h:outputText styleClass="outputText" value="Download Excel" 
							rendered="#{importPermitStatusAction.excelFlag==true}"
							style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
					</h:outputLink>



				</div>
				<div class="row " align="center">
					<rich:spacer height="20px"></rich:spacer>
				</div>
				<br />
			</div>
		</h:form>
	</f:view>
</ui:composition>