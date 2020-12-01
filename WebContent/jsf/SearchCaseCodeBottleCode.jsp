<ui:composition xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:a4j="http://richfaces.org/a4j"
	xmlns:rich="http://richfaces.org/rich">

	<f:view>
		<h:form>
			<div class="row col-md-12" align="center">
				<div><rich:spacer height="30px"></rich:spacer></div>
					<div><rich:spacer height="30px"></rich:spacer></div>
						<div><rich:spacer height="30px"></rich:spacer></div>	
	<div><rich:spacer height="50px"></rich:spacer></div>
					<div class="row col-md-12" >
						<div class="col-md-12" align="center">
							<h:outputText value="SEARCH FOR BARCODE AND BOTTLECODE"
										style="FONT-STYLE: italic; 
										COLOR: #0000a0; FONT-WEIGHT: bold; FONT-SIZE: x-large;"></h:outputText>
						</div>

					</div>
					
					<div class="col-md-12">
					<h:inputHidden>
						<h:messages errorStyle="color:red" layout="table" id="messages"
							infoStyle="color:red">
						</h:messages>
					</h:inputHidden>
				</div>
					<div><rich:spacer height="10px"></rich:spacer></div>
					<div><rich:separator lineType="dashed"></rich:separator></div>
					<div><rich:spacer height="30px"></rich:spacer></div>
					
			</div>
<div align="center">
<h:selectOneRadio styleClass="generalHeaderOutputTable" value="#{searchCaseCodeBottleCodeAction.radio}" onclick="this.form.submit();">
<f:selectItem itemLabel="BottleCode" itemValue="B" />
<f:selectItem itemLabel="CaseCode" itemValue="C"/>
</h:selectOneRadio>
</div>
<div align="center">
<div class="row">
<div class="col-md-4"></div>
<div class="col-md-4">
<h:inputText value="#{searchCaseCodeBottleCodeAction.casecode_bottle}"  class="form-control"/>
</div>
<div class="col-md-4"></div>
</div>
</div>


<div align="center">
<h:commandButton styleClass="btn btn-primary" value="Get Detail" action="#{searchCaseCodeBottleCodeAction.search}"></h:commandButton>
<h:commandButton styleClass="btn btn-danger" value="Reset Detail" action="#{searchCaseCodeBottleCodeAction.reset}"></h:commandButton>
</div>
		
		
	<rich:dataTable columnClasses="columnClass1" width="100%"
									headerClass="TableHead" footerClass="TableHead"
									rowClasses="TableRow1,TableRow2" styleClass="DataTable"
									id="table3" rows="10"  		
									value="#{searchCaseCodeBottleCodeAction.list}" var="list">

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
		
		
		
		</h:form>
	</f:view>
</ui:composition>