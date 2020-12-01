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
							<h:outputText value="Search the Brand"
								styleClass="generalHeaderOutputTable"
								style="font-weight: bold; font-size: 40px;text-decoration: underline;margin-top: 2px;font-family:Monotype Corsiva;font-size:40px;" />
						</h2>
					</div>
				</div>

				<div class="row " align="center">
					<rich:spacer height="30px"></rich:spacer>
				</div>
				
					<rich:spacer height="20px" style="BACKGROUND-COLOR: #dee0e2;"/>
				
					 <div class="row col-md-12" align="center" >
					<div class="col-md-12" align="center">
					<h:selectOneRadio style="FONT-WEIGHT: bold;  font-size: 18px; width: 25% "
						onclick="this.form.submit();"
						value="#{brandSearchAction.vch}"
							onchange="this.form.submit()"  
							valueChangeListener="#{brandSearchAction.radioListener}" >
					<f:selectItem itemValue="IMFL" itemLabel="FL" />
					<f:selectItem itemValue="CL" itemLabel="CL" />
					<f:selectItem itemValue="BEER" itemLabel="Beer" /> 
					<f:selectItem itemValue="LAB" itemLabel="Lab" />
					</h:selectOneRadio>



					</div>


				</div>
				

				<div class="row"  >
					<rich:spacer height="20px"></rich:spacer>
				</div>

				<div class="row col-md-12" align="center">
					<div class="row col-md-2" align="center"></div>
					<div class="row col-md-2" align="center"></div>
					<div class="row col-md-2" align="center">
					
					<h:outputLabel value="Type Brand Name" style="font-weight: bold" ></h:outputLabel>
					</div>
					
					<div class="row col-md-2" align="left">
					<h:inputText value="#{brandSearchAction.enterData}"></h:inputText>
					</div>
					<div class="row col-md-2" align="left">
					<h:commandButton value="search brand" class="btn btn-primary" action="#{brandSearchAction.getData}"> </h:commandButton>
					
					</div>
					<div class="row col-md-2" align="center"></div>
 					</div>
 				<div class="row"  >
					<rich:spacer height="20px"></rich:spacer>
				</div>

				<div>
						<rich:dataTable align="center" id="table57" rows="10" width="100%"
							var="listk"
							
							value="#{brandSearchAction.getVal}"
							headerClass="TableHead" footerClass="TableHead"
							rowClasses="TableRow1,TableRow2">

							<rich:column>
								<f:facet name="header">
									<h:outputLabel value="Sno" />
								</f:facet>
								<center>
									<h:outputText value="#{listk.sno}" />
								</center>
							</rich:column>

							<rich:column>
								<f:facet name="header">
									<h:outputLabel value="Brand Name" />
								</f:facet>
								<center>
									<h:outputText value="#{listk.brandName}" />
								</center>
							</rich:column>
                              
									<rich:column>
								<f:facet name="header">
									<h:outputLabel value="Size" />
								</f:facet>
								<center>
									<h:outputText value="#{listk.box_size}" />
								</center>
							</rich:column>
							
									<rich:column>
								<f:facet name="header">
									<h:outputLabel value="Package" />
								</f:facet>
								<center>
									<h:outputText value="#{listk.package_name}" />
								</center>
							</rich:column>
							
									<rich:column>
								<f:facet name="header">
									<h:outputLabel value="Etin No" />
								</f:facet>
								<center>
									<h:outputText value="#{listk.code_generate_through}" />
								</center>
							</rich:column>

							<f:facet name="footer">
								<rich:datascroller for="table57" />
							</f:facet>
						</rich:dataTable>
					</div>
			 
				
			
				
				<div class="row " align="center">
					<rich:spacer height="20px"></rich:spacer>
				</div>
				<br />
			</div>
		</h:form>
	</f:view>
</ui:composition>