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
					<rich:spacer height="10px"></rich:spacer>
				</div>
				<div class="row">
					<div class="col-md-12 wow shake" align="center">
						<h:messages errorStyle="color:red" layout="TABLE" id="messages1"
							infoStyle="color:green"
							style="font-size:18px; background-color:#e1fcdf; font-weight: bold">
						</h:messages>

					</div>
				</div>

				<hr style="border-top: 7px #D0D3D4; border-top-style: dashed;"></hr>
				<div class="row">
					<div align="center">
						<h:outputText value="Wholesale All Gatepass Cancellation"
							style="COLOR: #0000a0; FONT-WEIGHT: bold; FONT-SIZE: 35px;font-family:Comic Sans MS;"></h:outputText>
						<h:inputHidden value="#{wholesaleCancelAllGatepassAction.hidden}"></h:inputHidden>
					</div>
				</div>
				<hr style="border-top: 7px #D0D3D4; border-top-style: dashed;"></hr>

				<div class="row " align="center">
					<rich:spacer height="10px"></rich:spacer>
				</div>

				<div class="row col-md-12" align="center"
					style="FONT-STYLE: italic; FONT-WEIGHT: bold;">
					Select Date :
					<rich:calendar value="#{wholesaleCancelAllGatepassAction.crDate}"
						valueChangeListener="#{wholesaleCancelAllGatepassAction.dateListener}"
						onchanged="this.form.submit();"></rich:calendar>
				</div>


				<div class="row " align="center">
					<rich:spacer height="40px"></rich:spacer>
				</div>

				<div class="row">
					<div class="col-md-12">
						<div style="overflow-x: scroll; width: 100%; height: 400px">
							<rich:dataTable align="center" styleClass="table-responsive"
								width="100%" id="rentopiccat"
								value="#{wholesaleCancelAllGatepassAction.displayAllGatepass}"
								var="list" style="FONT-FAMILY: 'Baskerville Old Face';"
								headerClass="TableHead" footerClass="TableHead"
								rowClasses="TableRow1,TableRow2">

								<rich:column id="column1">
									<f:facet name="header">
										<h:outputText style="white-space: normal; font-weight: bold;"
											value="Sr.No." />
									</f:facet>
									<center>
										<h:outputText styleClass="generalExciseStyle"
											value="#{list.slNo}" />
									</center>
								</rich:column>

								<rich:column>
									<f:facet name="header">
										<h:outputText value="Unit Type"
											style="white-space: normal; font-weight: bold;" />
									</f:facet>
									<center>
										<h:outputText styleClass="generalExciseStyle"
											value="#{list.unitType_dt}" />
									</center>
								</rich:column>

								<rich:column>
									<f:facet name="header">
										<h:outputText value="Unit Name"
											style="white-space: normal; font-weight: bold;" />
									</f:facet>
									<center>
										<h:outputText styleClass="generalExciseStyle"
											value="#{list.unitName_dt}" />
									</center>
								</rich:column>

								<rich:column id="column82">
									<f:facet name="header">
										<h:outputText value="Gatepass Date"
											style="white-space: normal; font-weight: bold;">
										</h:outputText>
									</f:facet>
									<h:outputText styleClass="generalExciseStyle"
										value="#{list.gatepassDate_dt}">
									</h:outputText>
								</rich:column>
								<rich:column id="column3">
									<f:facet name="header">
										<h:outputText value="GatePass No."
											style="white-space: normal; font-weight: bold;" />
									</f:facet>
									<center>
										<h:outputText styleClass="generalExciseStyle"
											value="#{list.gatepassNmbr_dt}" />
									</center>
								</rich:column>

								<rich:column>
									<f:facet name="header">									 
										<h:outputText value="Select"
											style="white-space: normal; font-weight: bold;"></h:outputText>
									</f:facet>
									<h:selectBooleanCheckbox value="#{list.checkBox}" />
								</rich:column>

								
							</rich:dataTable>
						</div>
					</div>
				</div>

				<div class="row " align="center">
					<rich:spacer height="20px"></rich:spacer>
				</div>

				<br />
				<div class="row" align="center">

					<h:commandButton value="Cancel All" styleClass="btn btn-danger"
						action="#{wholesaleCancelAllGatepassAction.cancelGatepass}">
					</h:commandButton>
				</div>


				<div class="row " align="center">
					<rich:spacer height="20px"></rich:spacer>
				</div>
				<br />
			</div>
		</h:form>
	</f:view>
</ui:composition>