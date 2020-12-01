<ui:composition xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:a4j="http://richfaces.org/a4j"
	xmlns:rich="http://richfaces.org/rich">
	<f:view>
		<h:form>
			<div class="panel panel-default">
				<div class="panel-body">
					
					
						<div class="row col-md-12" align="center" >
						<div class="col-md-12" >
							<h:inputHidden><h:messages errorStyle="color:red" layout="table"
														id="messages" infoStyle="color:red">
													</h:messages></h:inputHidden>
						</div>

					</div>
						<div><rich:spacer height="50px"></rich:spacer></div>
					<div class="row col-md-12" >
						<div class="col-md-12" align="center">
							<h:outputText value="SEARCH FOR BARCODE AND BOTTLECODE"
										style="FONT-STYLE: italic; 
										COLOR: #0000a0; FONT-WEIGHT: bold; FONT-SIZE: x-large;"></h:outputText>
						</div>

					</div>
					<div><rich:spacer height="10px"></rich:spacer></div>
					<div><rich:separator lineType="dashed"></rich:separator></div>
					<div><rich:spacer height="30px"></rich:spacer></div>
					
						<div>
						<div class="col-md-12" align="center">
						
							<h:selectOneRadio value="#{search_For_BarCode_BottleCode_Action.radio}" 
							style="FONT-WEIGHT: bold;  font-size: 20px;  " 
								valueChangeListener="#{search_For_BarCode_BottleCode_Action.stateCountryChange}"
							onchange="this.form.submit();"
							>
								<f:selectItem itemValue="BRC" itemLabel="BarCode" />
								<f:selectItem itemValue="BCOD" itemLabel="BottleCode" />
							</h:selectOneRadio>
						</div>

					</div>
					
						<div><rich:spacer height="30px"></rich:spacer></div>
					
					<div align="center" 
												style="background-color: #D0D3D4; padding: 5; " >
												<div><rich:spacer height="20px"></rich:spacer></div>
														<div>Case Code : 
														<h:inputText style="width: 250px"
																value="#{search_For_BarCode_BottleCode_Action.bottleCode}"/>
																
																<h:commandButton style="width: 150px"
																action="#{search_For_BarCode_BottleCode_Action.checkBottleCode}"
																value="Get Details"
																styleClass="btn btn-success" />
																
																</div>
																<rich:spacer height="20px"></rich:spacer>		
																<div></div>			
												
												</div>
					<rich:spacer height="30px"></rich:spacer>
		
					<div align="center">
								<rich:dataTable columnClasses="columnClass1" width="100%"
									headerClass="TableHead" footerClass="TableHead"
									rowClasses="TableRow1,TableRow2" styleClass="DataTable"
									id="table3" rows="10"  		
									value="#{search_For_BarCode_BottleCode_Action.searchDisplaylist}" var="list">

									<rich:column>
										<f:facet name="header">
											<h:outputLabel value="Sno" styleClass="generalHeaderStyle" />
										</f:facet>
										<center>
											<h:outputText styleClass="generalExciseStyle"
												value="#{list.slno}" />
										</center>
									</rich:column>
									<rich:column sortBy="#{list.etinNmbr}" filterBy="#{list11.etinNmbr}">
										<f:facet name="header">
											<h:outputText value="ETIN No."
												styleClass="generalHeaderOutputTable" />
										</f:facet>
										<h:outputText value="#{list.etinNmbr}">
										</h:outputText>
									</rich:column>

									<rich:column>
										<f:facet name="header">
											<h:outputText value="Unit Name"
												styleClass="generalHeaderOutputTable" />
										</f:facet>
										<h:outputText value="#{list.unit_Name}">
										</h:outputText>
									</rich:column>

								



									<rich:column rendered="#{search_For_BarCode_BottleCode_Action.radio eq 'BCOD'}">
										<f:facet name="header">
											<h:outputText value="BottleCode"
												styleClass="generalHeaderOutputTable" />
										</f:facet>
										<h:outputText value="#{list.bottleCode}"   >
										</h:outputText>
									</rich:column>
									
									<rich:column sortBy="#{list.caseCode}" filterBy="#{list11.caseCode}" >
										<f:facet name="header">
											<h:outputText value="CaseCode"
												styleClass="generalHeaderOutputTable" />
										</f:facet>
										<h:outputText value="#{list.caseCode}" >

										</h:outputText>
									</rich:column>


									
									<rich:column>
										<f:facet name="header">
											<h:outputText value="FL11 Gatepass"
												styleClass="generalHeaderOutputTable" />
										</f:facet>
										<h:outputText value="#{list.fl11gatepass}" >
										</h:outputText>

									</rich:column>


									<rich:column >
										<f:facet name="header">
											<h:outputText value="FL11 Date"
												styleClass="generalHeaderOutputTable" />
										</f:facet>
										<h:outputText value="#{list.fl11_date}" >
										</h:outputText>
									</rich:column>


									

									<rich:column sortBy="#{list.fl36Gatepass}" filterBy="#{list11.fl36Gatepass}">
										<f:facet name="header">
											<h:outputText value="FL36 Gatepass"
												styleClass="generalHeaderOutputTable" />
										</f:facet>
										<h:outputText value="#{list.fl36Gatepass}" >
										</h:outputText>

									</rich:column>


									<rich:column sortBy="#{list.fl36Date}">
										<f:facet name="header">
											<h:outputText value="FL36 Date"
												styleClass="generalHeaderOutputTable" />
										</f:facet>
										<h:outputText value="#{list.fl36Date}" >
										</h:outputText>
									</rich:column>


									<rich:column>
										<f:facet name="header">
											<h:outputText value="Dispatch From Wholesale"
												styleClass="generalHeaderOutputTable" />
										</f:facet>
										<h:outputText value="#{list.ws_gatepass}" >
										</h:outputText>

									</rich:column>


									<rich:column>
										<f:facet name="header">
											<h:outputText value="Dispatch Date"
												styleClass="generalHeaderOutputTable" />
										</f:facet>
										<h:outputText value="#{list.ws_date}" >
										</h:outputText>
									</rich:column>

									<f:facet name="footer">
										<rich:datascroller for="table3"></rich:datascroller>
									</f:facet>
								</rich:dataTable>
				</div>
					<rich:spacer height="20px"></rich:spacer>
					<div  align="center" >
							<rich:spacer width="10px"></rich:spacer>
								<h:commandButton styleClass="btn btn-warning"
									action="#{search_For_BarCode_BottleCode_Action.reset}" value="Reset"></h:commandButton>
					</div>
							<rich:spacer height="20px"></rich:spacer>
					
				</div>
			</div>
		</h:form>
	</f:view>
</ui:composition>