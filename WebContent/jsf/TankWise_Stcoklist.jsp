<ui:composition xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:a4j="http://richfaces.org/a4j"
	xmlns:rich="http://richfaces.org/rich">
	<f:view>
	
		<h:form style="background-color:whitesmoke;">
<head>
	<style>

	.striped-border 
	{ border: 1px dashed #000; width: 50%; margin: auto; margin-top: 5%; margin-bottom: 5%; }
	.example3 {
	border: 0;
	height: 1px;
	background: #000066;
}


	</style>
	
	
	</head>
			<div class="form-group">
				<div class="row">
					<a4j:outputPanel id="msg">
						<div class="row col-md-12 wow shake" style="margin-top: 10px;">
							<h:messages errorStyle="color:red" style="font-size: 18px;"
								styleClass="generalExciseStyle" layout="table"
								infoStyle="color:green" />
						</div>
					</a4j:outputPanel>
				</div>
				<div class="row " align="center">
					<rich:spacer height="10px"></rich:spacer>
				</div>
				<div class="row">
					<div align="center">
						<h:outputText value="Tank Wise Stock Register" 
  
							style="COLOR: white; text-shadow: 2px 2px 4px #000000;   FONT-WEIGHT: bold; FONT-SIZE: 35px;font-family:Comic Sans MS;"></h:outputText>
						<h:inputHidden value="#{tankWise_StcoklistAction.hidden}"></h:inputHidden>
					</div>
				</div>

				<hr class="example3" />
				<div class="row">
					<div align="center">
						<h:outputLabel value="#{tankWise_StcoklistAction.loginUserNm}"
							style="FONT-FAMILY: 'Elephant'; COLOR: #000066; FONT-STYLE: italic;FONT-SIZE: x-large;"></h:outputLabel>
					</div>
				</div>
				<div class="row " align="center">
					<rich:spacer height="3px"></rich:spacer>
				</div>
				<div class="row">
					<div align="center">
						<h:outputLabel value="#{tankWise_StcoklistAction.loginUserAdrs}"
							style="FONT-FAMILY: 'Elephant'; COLOR: #000066; FONT-STYLE: italic;width: 100%;FONT-SIZE: medium;"></h:outputLabel>
					</div>
				</div>

				<hr class="example3" />

				<div class="row " align="center">
					<rich:spacer height="20px"></rich:spacer>
				</div>

				<div class="row col-md-12" align="center"

					style="BACKGROUND-COLOR:#ffcc99;box-shadow: 5px 5px 10px;COLOR:#14141f;  FONT-WEIGHT: bold;">
					<div class="col-md-12" align="center" >
						<h:selectOneRadio value="#{tankWise_StcoklistAction.radio}" 
							valueChangeListener="#{tankWise_StcoklistAction.change_radio}"
							onchange="this.form.submit();">

							<f:selectItem  itemValue="SV" itemLabel="Spirit VAT" />
							<f:selectItem itemValue="DV" itemLabel="Denatured Spirit VAT" />
							<f:selectItem itemValue="BLENDCL" itemLabel="Blending CL" />
							<f:selectItem itemValue="BLENDFL" itemLabel="Blending FL" />
							<f:selectItem itemValue="BOTCL" itemLabel="Bottling CL" />
							<f:selectItem itemValue="BOTFL" itemLabel="Bottling FL" />
						</h:selectOneRadio>
					</div>

				</div>
				<div class="row " align="center">
					<rich:spacer height="20px"></rich:spacer>
				</div>
				<div class="co-md-12 row">

					<div class="col-md-4" align="right">
						<h:outputText value="Vat List:" style="FONT-STYLE: italic; FONT-WEIGHT: bold;"
							styleClass="generalHeaderOutputTable"></h:outputText>
					</div>
					<div class="col-md-5" align="left">
						<h:selectOneMenu styleClass="form-control"
						 style="COLOR: #0000ff;FONT-STYLE: italic;FONT-SIZE: 15px;"
							onchange="this.form.submit();"
							value="#{tankWise_StcoklistAction.vatNo}">
							<f:selectItems value="#{tankWise_StcoklistAction.vatList}" />

						</h:selectOneMenu>

					</div>
				</div>
				<div class="row " align="center">
					<rich:spacer height="20px"></rich:spacer>
				</div>

				<div class="col-md=12" align="center"
					style="FONT-STYLE: italic; FONT-WEIGHT: bold;">


					<h:outputText>Form Date : </h:outputText>

					<rich:calendar  
					value="#{tankWise_StcoklistAction.formdate}"></rich:calendar>
					<rich:spacer width="30px"></rich:spacer>
					<h:outputText>To Date : </h:outputText>
					<rich:calendar value="#{tankWise_StcoklistAction.todate}"></rich:calendar>

				</div>
				<div>
					<rich:spacer height="20px"></rich:spacer>
				</div>
				<div class="row" align="center">

					<h:commandButton value="Show Data" styleClass="btn-sm btn-success"
						action="#{tankWise_StcoklistAction.showdata}">
					</h:commandButton>

			
                    
					<rich:spacer width="20px"></rich:spacer>
				
					<h:commandButton action="#{tankWise_StcoklistAction.reset}"
						value="Reset" styleClass="btn-sm btn-warning" />

				</div>
				<div>
				<rich:spacer height="20px"></rich:spacer>
				
				</div>
				                  		<div class="row" align="left">
			                 
								</div>
								
								<h:panelGroup   rendered="#{tankWise_StcoklistAction.viewflg eq 'T'}">
											<div class="col-md-12" align="center">
						<div class="col-md-3" align="right">
							<h:outputText value="Opening (BL) ON 16 July:  "
									style="FONT-WEIGHT: bold; font-size: 15px"></h:outputText>
							</div>
							<div class="col-md-3" align="left">
							
						<h:inputText
									value="#{tankWise_StcoklistAction.opening_bl}"
									style="COLOR: #0000ff;" readonly="true" styleClass="form-control"></h:inputText>
						</div>
							<div class="col-md-3" align="right">
								<h:outputText value="Opening (AL) ON 16 July::  "
									style="FONT-WEIGHT: bold; font-size: 15px"></h:outputText>
							</div>
							<div class="col-md-3" align="left">
								<h:inputText
									value="#{tankWise_StcoklistAction.opening_al}"
									style="COLOR: #0000ff;" readonly="true" styleClass="form-control"></h:inputText>
						
						</div>
						</div>
						
							<div>
				<rich:spacer height="20px"></rich:spacer>
				
				</div>
					<div class="col-md-12" align="center">
					<rich:dataTable columnClasses="columnClass1"
						headerClass="TableHead" footerClass="TableHead"
						rowClasses="TableRow1,TableRow2" styleClass="DataTable"
						id="table303" width="100%" align="center"  rows="10"
						value="#{tankWise_StcoklistAction.showDataTablelist}" var="list">

						<f:facet name="header">
							<h:outputText value="Pending"
								styleClass="generalHeaderOutputTable"></h:outputText>

							<rich:columnGroup>
								<rich:column rowspan="2" style="text-align: center;">
									<h:outputText value="Sr No." />
								</rich:column>
								<rich:column rowspan="2" style="text-align: center;">
									<h:outputText value="Date" />
								</rich:column>
								<rich:column rowspan="2" style="text-align: center;">
									<h:outputText value="Description" />
								</rich:column>

								<rich:column colspan="2" style="text-align: center;">
									<h:outputText value=" Wastage" />
								</rich:column>
								<rich:column colspan="2" style="text-align: center;">
									<h:outputText value=" Recieve" />
								</rich:column>

								<rich:column colspan="2" style="text-align: center;">
									<h:outputText value="Consumed " />
								</rich:column>


								<rich:column rowspan="2" style="text-align: center;">
									<h:outputText value="Balance BL" />
								</rich:column>

								<rich:column rowspan="2" style="text-align: center;">
									<h:outputText value="Balance AL" />
								</rich:column>


								<rich:column breakBefore="true">
									<h:outputText value="BL" />
								</rich:column>
								<rich:column>
									<h:outputText value="AL " />
								</rich:column>

								<rich:column>
									<h:outputText value="BL" />
								</rich:column>
							
								<rich:column>
									<h:outputText value="AL " />
								</rich:column>



								<rich:column>
									<h:outputText value=" BL " />
								</rich:column>
								<rich:column>
									<h:outputText value="  AL " />
								</rich:column>



								
							</rich:columnGroup>
						</f:facet>

						<rich:column>
							<h:outputText value="#{list.srNo}"></h:outputText>
						</rich:column>

         	            <rich:column>
							<h:outputText value="#{list.date}" styleClass="form-control" >
														<f:convertDateTime pattern="dd-M-yyyy"
															timeZone="GMT+05:30" />
													</h:outputText>
						</rich:column>

						<rich:column>
								<a4j:outputPanel>
														<h:outputText value="#{list.discription}"
															styleClass="form-control"></h:outputText>
													</a4j:outputPanel>
						</rich:column>


						<rich:column>
						<h:outputText value="#{list.wast_bl}"
														styleClass="form-control"></h:outputText>

						</rich:column>
						<rich:column>

						<h:outputText value="#{list.wast_al}"
														styleClass="form-control"></h:outputText>
						</rich:column>



						<rich:column >
						<h:outputText value="#{list.recv_bl}"  styleClass="form-control"
														 ></h:outputText>
						</rich:column>
					
						<rich:column>
							<h:outputText value="#{list.recv_al}" styleClass="form-control"
														 ></h:outputText>
						</rich:column>


						<rich:column>
							<h:outputText value="#{list.cosum_bl}" styleClass="form-control" ></h:outputText>
						</rich:column>
						<rich:column>
						<h:outputText value="#{list.cosum_al}" styleClass="form-control" ></h:outputText>
						</rich:column>



						<rich:column>
								<h:outputText value="#{list.tot_bl}"  styleClass="form-control" >
													
															<f:convertNumber maxFractionDigits="2"
											pattern="#############0.00" /></h:outputText>
						</rich:column>


						<rich:column>
						<h:outputText value="#{list.tot_al}" styleClass="form-control" >
													
															<f:convertNumber maxFractionDigits="2"
											pattern="#############0.00" /></h:outputText>

						</rich:column>





											<f:facet name="footer">
									<rich:datascroller for="table303"></rich:datascroller>
								</f:facet>
					</rich:dataTable>
				</div>
					
											</h:panelGroup>
			</div>
		
		</h:form>
	</f:view>
</ui:composition>