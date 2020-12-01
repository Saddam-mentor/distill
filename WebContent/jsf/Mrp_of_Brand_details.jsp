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

				<div class="row " align="center">
					<div align="center" width="100%" class="pghead">
						<h2>
							<h:outputText value=" MRP Of Brand Details "
								styleClass="generalHeaderOutputTable"
								style="font-weight: bold; font-size: 40px;text-decoration: underline;margin-top: 2px;font-family:Monotype Corsiva;font-size:40px;" />
						</h2>
					</div>
				</div>

				<div class="row " align="center">
					<rich:spacer height="30px"></rich:spacer>
				</div>

				<div class="row col-md-12" align="center"
					style="BACKGROUND-COLOR: #dee0e2;">
					<div class="col-md-12" align="center">
						<h:selectOneRadio
							style="FONT-WEIGHT: bold;  font-size: 18px;  "
							valueChangeListener="#{mrp_of_Brand_details_action.changeRadio}"
							value="#{mrp_of_Brand_details_action.radio}"
							onchange="this.form.submit();">
							
							<f:selectItem itemValue="D" itemLabel="Distillery" />
							<f:selectItem itemValue="B" itemLabel="Brewery" />
							<f:selectItem itemValue="BWFL" itemLabel="BWFL " />
				<f:selectItem itemValue="IU" itemLabel="Importing Unit " />
						</h:selectOneRadio>
					</div>
				</div>

				<div class="row">
					<rich:spacer height="20px"></rich:spacer>
				</div> 
				
				
				
				<div class="row">
					 
                     
					<div align="center">

						<rich:dataTable var="list"
							value="#{mrp_of_Brand_details_action.showData}" width="100%"
							id="table5" rows="10" headerClass="TableHead"
							footerClass="TableHead" rowClasses="TableRow1,TableRow2">
							<rich:column >
								<f:facet name="header">
									<h:outputText value="Sr No" />
								</f:facet>
								<h:outputText value="#{list.srNO}" style="align:center">
								</h:outputText>
							</rich:column>
							<rich:column sortBy="#{list.unitName}">
								<f:facet name="header">
									<h:outputText value="Unit Name" />
								</f:facet>
								<h:outputText value="#{list.unitName}" />
							</rich:column>
							
							<rich:column filterBy="#{list.brandName}" filterEvent="onkeyup" sortBy="#{list.brandName}" >
								<f:facet name="header">
									<h:outputText value="Brand Name" />
								</f:facet>
								<h:outputText value="#{list.brandName}" />
							</rich:column>
							
							<rich:column >
								<f:facet name="header">
									<h:outputText value="Size" />
								</f:facet>
								<h:outputText value="#{list.size}" />
							</rich:column>

<rich:column filterBy="#{list.brandName}" filterEvent="onkeyup" sortBy="#{list.type}" >
								<f:facet name="header">
									<h:outputText value="Type" />
								</f:facet>
								<h:outputText value="#{list.type}" />
							</rich:column>
							

							<rich:column sortBy="#{list.mrp}">
								<f:facet name="header">
									<h:outputText value="MRP" />
								</f:facet>
								<h:outputText value="#{list.mrp}" />
							</rich:column>
							
							<rich:column sortBy="#{list.renued}">
								<f:facet name="header">
									<h:outputText value="Renewed" />
								</f:facet>
								<h:outputText value="#{list.renew}" />
							</rich:column>
							

							<f:facet name="footer">
								<rich:datascroller for="table5" />
							</f:facet>
						</rich:dataTable>

					</div>
				</div>
				

				

				<div class="row " align="center">
					<rich:spacer height="30px"></rich:spacer>
				</div>
				
				
				<div class="col-md-12" align="center">
					<h:commandButton action="#{mrp_of_Brand_details_action.excel}"
						value="Generate Excel" styleClass="btn btn-success" />
						
					<h:outputLink styleClass="outputLinkEx"
						value="/doc/ExciseUp//MIS//Excel//#{mrp_of_Brand_details_action.exlname}"
						target="_blank">
						
						<h:outputText styleClass="outputText" value="Download Excel" 
							rendered="#{mrp_of_Brand_details_action.excelFlag==true}"
							style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
					</h:outputLink>
					
				
							<rich:spacer width="10px"></rich:spacer>
								<h:commandButton styleClass="btn btn-warning"
									action="#{mrp_of_Brand_details_action.reset}" value="Reset"></h:commandButton>
					
				</div>
				
			</div>
		</h:form>
	</f:view>
</ui:composition>