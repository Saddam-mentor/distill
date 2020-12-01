<ui:composition xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:a4j="http://richfaces.org/a4j"
	xmlns:rich="http://richfaces.org/rich">
	<f:view>
		<h:form style="background-color:whitesmoke;">

<style>
 
 .selectItem{ margin:5px;}
</style>
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

				<div class="row " align="center">
					<div align="center" width="100%" class="pghead">
					<h:inputHidden
									value="#{gatepassReceivedAtWholesaleAction.hidden}"></h:inputHidden>
						<h2>
							<h:outputText value=" Gatepasses Received At Wholesale "
								styleClass="generalHeaderOutputTable"
								style="font-weight: bold; font-size: 40px;text-decoration: underline;margin-top: 2px;font-family:Monotype Corsiva;font-size:40px;" />
						</h2>
					</div>
					
					
					
				</div>
				
				<div align="center">
				  <h:selectOneRadio value="#{gatepassReceivedAtWholesaleAction.radio}"> 
				    <f:selectItem itemLabel="Distillery FL" itemValue="D"/> 
				    <f:selectItem itemLabel="Distillery CL" itemValue="CL"/>
				    <f:selectItem itemLabel="BWFL" itemValue="BWFL"/>
				    <f:selectItem itemLabel="Beer" itemValue="BEER"/>
				    <f:selectItem itemLabel="FL2D" itemValue="FL2D"/>
				  </h:selectOneRadio>
				</div>

				<div class="row " align="center">
					<rich:spacer height="30px"></rich:spacer>
				</div>

				<div class="col-md-12" align="center">
					<div class="col-md-5" align="right">
						<h:outputText value="Wholesale Inspector:"
							></h:outputText>
					</div>
					<div class="col-md-3" align="left">
						<h:selectOneMenu
							
							value="#{gatepassReceivedAtWholesaleAction.inspector_id}"
							style="width: 250px; height: 28px;" >
							<f:selectItems
								value="#{gatepassReceivedAtWholesaleAction.inspectorList}" />
						</h:selectOneMenu>
					</div>
				</div>

				<div class="row">
					<rich:spacer height="20px"></rich:spacer>
				</div>

				<div class="col-md=12" align="center">
					<div class="col-md-2"></div>
					<div class="col-md-3" align="right"> Gatepass Received At Wholesale Between</div>
					<div class="col-md-1" align="left">
						<rich:calendar value="#{gatepassReceivedAtWholesaleAction.fromDate}">
						</rich:calendar>
					</div>
					<div class="col-md-1" align="right">And</div>
					<div class="col-md-1" align="left">
						<rich:calendar value="#{gatepassReceivedAtWholesaleAction.toDate}">
						</rich:calendar>
					</div>
					<div class="col-md-4"></div>
				</div>

				<div class="row " align="center">
					<rich:spacer height="30px"></rich:spacer>
				</div>
				<br /> <br />
				<div class="row" align="center">
					<h:commandButton value="Print Report" styleClass="btn btn-success"
						action="#{gatepassReceivedAtWholesaleAction.print}">
					</h:commandButton>

					<h:outputLink styleClass="outputLinkEx"
						value="/doc/ExciseUp/MIS/pdf/#{gatepassReceivedAtWholesaleAction.pdfName}"
						target="_blank">
						<h:outputText styleClass="outputText" id="text223"
							value="View Report"
							rendered="#{gatepassReceivedAtWholesaleAction.printFlag==true}"
							style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
					</h:outputLink>

					<rich:spacer width="10px;"></rich:spacer>
					<h:commandButton value="Reset" styleClass="btn btn-warning"
						action="#{gatepassReceivedAtWholesaleAction.reset}">
					</h:commandButton>
				</div>
				<br />
				
				<div class="row " align="center">
					<rich:spacer height="20px"></rich:spacer>
				</div>
				<br />
			</div>
		</h:form>
	</f:view>
</ui:composition>