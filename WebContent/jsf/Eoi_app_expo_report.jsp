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
							<h:outputText
								value="Export Outside India Report For Distillery"
								styleClass="generalHeaderOutputTable"
								style="font-weight: bold; font-size: 40px;text-decoration: underline;margin-top: 2px;font-family:Monotype Corsiva;font-size:40px;" />
						</h2>
					</div>
				</div>

				<div class="row " align="center">
					<rich:spacer height="30px"></rich:spacer>
				</div>

				
				<div class="row">
					<rich:spacer height="20px"></rich:spacer>
				</div>

				<div class="col-md-12" align="center">
					<div class="col-md-6" align="right">
						<h:outputText value="Select Distillery Name:"
							style="FONT-SIZE: small; FONT-WEIGHT: bold;"></h:outputText>
					      </div>
					<div class="col-md-6" align="left">
						<h:selectOneMenu
							value="#{eoi_app_expo_report_action.distilleryId}"
							onchange="this.form.submit();"							
							style="width: 250px; height: 28px;">
							<f:selectItems
								value="#{eoi_app_expo_report_action.distilleryList}"/>
						</h:selectOneMenu>
					</div>
				</div>
           <div class="row">
					<rich:spacer height="40px"></rich:spacer>
				</div>
                    <div class="row col-md-12" align="center"
					style="BACKGROUND-COLOR: #dee0e2;">
					<div class="col-md-12" align="center">
						<h:selectOneRadio style="FONT-WEIGHT: bold;  font-size: 15px; "
							value="#{eoi_app_expo_report_action.radio}"
							valueChangeListener="#{eoi_app_expo_report_action.radioListener}"
							onchange="this.form.submit();">
							<f:selectItem itemValue="BA" itemLabel=" Export Order Before Approval" />
							<f:selectItem itemValue="AA" itemLabel=" Export Order After Approval" />
						</h:selectOneRadio>
					</div>

				</div>
		</div>
		
		<rich:spacer height="40px"></rich:spacer>
		
		<div align="center">
			<rich:dataTable columnClasses="columnClass1"
													headerClass="TableHead" footerClass="TableHead"
													rowClasses="TableRow1,TableRow2" styleClass="table table-hover"
													width="90%" rows="15"
													rendered="#{eoi_app_expo_report_action.radio eq 'BA'}"
													value="#{eoi_app_expo_report_action.displaylist}" var="list">
                                                    <rich:column>
														<f:facet name="header">
															<h:outputLabel value="Sr.NO"
																styleClass="generalHeaderStyle" style="FONT-SIZE: small;"/>
														</f:facet>
														<center>
															<h:outputText styleClass="generalExciseStyle"
															  value="#{list.sr_no}" />
														</center>
													</rich:column>
													<rich:column>
														<f:facet name="header">
															<h:outputLabel value="FLB11 Number"
																styleClass="generalHeaderStyle" style="FONT-SIZE: small;"/>
														</f:facet>
														<center>
															<h:outputText styleClass="generalExciseStyle"
															  value="#{list.flb11_no}" />
														</center>
													</rich:column>

													<rich:column>
														<f:facet name="header">
															<h:outputLabel value="FLB11 Date"
															
																styleClass="generalHeaderStyle" style="FONT-SIZE: small;"/>
														</f:facet>
														<center>
															<h:outputText styleClass="inputtext"
															value="#{list.flb11_dt}" />
														</center>
													</rich:column>

													
												
													<rich:column>
														<f:facet name="header">
															<h:outputLabel value="Shipping Bill No"
																styleClass="generalHeaderStyle" style="FONT-SIZE: small;"/>
														</f:facet>
														<center>
															<h:outputText styleClass="inputtext"
															  value="#{list.shipp_no}" />
														</center>
													</rich:column>
													
													<rich:column>
														<f:facet name="header">
															<h:outputLabel value="Shipping Bill Date"
																styleClass="generalHeaderStyle" style="FONT-SIZE: small;"/>
														</f:facet>
														<center>
															<h:outputText styleClass="generalExciseStyle"
															  value="#{list.exp_no}" />
														</center>
													</rich:column>
													
													<rich:column>
														<f:facet name="header">
															<h:outputLabel value="Bond Received Date"
																styleClass="generalHeaderStyle" style="FONT-SIZE: small;"/>
														</f:facet>
														<center>
															<h:outputText styleClass="inputtext"
															  value="#{list.bond_dt}" />
														</center>
													</rich:column>
													
													<rich:column>
														<f:facet name="header">
															<h:outputLabel value="BRC No."
																styleClass="generalHeaderStyle" style="FONT-SIZE: small;"/>
														</f:facet>
														<center>
															<h:outputText styleClass="inputtext"
															  value="#{list.brc_no}" />
														</center>
													</rich:column>
													
													<rich:column>
														<f:facet name="header">
															<h:outputLabel value="BRC date"
																styleClass="generalHeaderStyle" style="FONT-SIZE: small;"/>
														</f:facet>
														<center>
															<h:outputText styleClass="inputtext"
															  value="#{list.brc_dt}" />
														</center>
													</rich:column>
												  <f:facet name="footer">
												  <rich:datascroller></rich:datascroller>
													</f:facet>
												</rich:dataTable>
												</div>
												
												<rich:spacer height="40px"></rich:spacer>
												
												<div align="center">
			<rich:dataTable columnClasses="columnClass1"
													headerClass="TableHead" footerClass="TableHead"
													rowClasses="TableRow1,TableRow2" styleClass="table table-hover"
													width="90%" rows="10"
													rendered="#{eoi_app_expo_report_action.radio eq 'AA'}"
													value="#{eoi_app_expo_report_action.displaylist1}" var="list">
                                                    <rich:column>
														<f:facet name="header">
															<h:outputLabel value="Sr.NO"
																styleClass="generalHeaderStyle" style="FONT-SIZE: small;"/>
														</f:facet>
														<center>
															<h:outputText styleClass="generalExciseStyle"
															  value="#{list.sr_n}" />
														</center>
													</rich:column>
													<rich:column>
														<f:facet name="header">
															<h:outputLabel value="Import Order No"
																styleClass="generalHeaderStyle" style="FONT-SIZE: small;"/>
														</f:facet>
														<center>
															<h:outputText styleClass="generalExciseStyle"
															  value="#{list.imp_no}" />
														</center>
													</rich:column>

													<rich:column>
														<f:facet name="header">
															<h:outputLabel value="Date"
																styleClass="generalHeaderStyle" style="FONT-SIZE: small;"/>
														</f:facet>
														<center>
															<h:outputText styleClass="inputtext"
															value="#{list.imp_dt}" />
														</center>
													</rich:column>

													<rich:column>
														<f:facet name="header">
															<h:outputLabel value="Country Name"
															
																styleClass="generalHeaderStyle" style="FONT-SIZE: small;"/>
														</f:facet>
														<center>
															<h:outputText styleClass="inputtext"
															value="#{list.country}" />
														</center>
													</rich:column>
												
													<rich:column>
														<f:facet name="header">
															<h:outputLabel value="Importing Unit Name"
																styleClass="generalHeaderStyle" style="FONT-SIZE: small;"/>
														</f:facet>
														<center>
															<h:outputText styleClass="inputtext"
															  value="#{list.import_unit}" />
														</center>
													</rich:column>
													
													
													
													
													
													<rich:column align="center">
							<f:facet name="header">
								<h:outputText value="View"
									
									styleClass="generalHeaderOutputTable"></h:outputText>
							   </f:facet>
							   <h:commandButton value="View Details" 
							   actionListener="#{eoi_app_expo_report_action.view}" 
							   styleClass="btn btn-primary btn-sm"/>
						       </rich:column>
								 <f:facet name="footer">
									 <rich:datascroller></rich:datascroller>
													</f:facet>
										</rich:dataTable>
								</div>
								
								<rich:spacer height="40px"></rich:spacer>
					<h:panelGroup rendered="#{eoi_app_expo_report_action.flag1==true}">			
								<div align="center">
								
								<rich:spacer height='20px'></rich:spacer>
								
								<div align="center" style="background-color:#F4F3CC;">
				  Import Order  No. :-  <h:outputText value="#{eoi_app_expo_report_action.import_no}"
				  style="color:blue;"> </h:outputText>
				  <rich:spacer width='30px'></rich:spacer>
				   Import Order Date. :-  <h:outputText value="#{eoi_app_expo_report_action.import_date}"
				  style="color:blue;"> </h:outputText>
				</div>
					<rich:spacer height='20px'></rich:spacer>
								
			<rich:dataTable columnClasses="columnClass1"
													headerClass="TableHead" footerClass="TableHead"
													rowClasses="TableRow1,TableRow2" styleClass="table table-hover"
													width="90%" rows="10"
													value="#{eoi_app_expo_report_action.displaylist2}" var="list">
                                                    <rich:column>
														<f:facet name="header">
															<h:outputLabel value="Sr.NO"
																styleClass="generalHeaderStyle" style="FONT-SIZE: small;"/>
														</f:facet>
														<center>
															<h:outputText styleClass="generalExciseStyle"
															  value="#{list.sr}" />
														</center>
													</rich:column>
													<rich:column>
														<f:facet name="header">
															<h:outputLabel value="Export Order No"
																styleClass="generalHeaderStyle" style="FONT-SIZE: small;"/>
														</f:facet>
														<center>
															<h:outputText styleClass="generalExciseStyle"
															  value="#{list.ex_no}" />
														</center>
													</rich:column>

													<rich:column>
														<f:facet name="header">
															<h:outputLabel value=" Export Order Date"
																styleClass="generalHeaderStyle" style="FONT-SIZE: small;"/>
														</f:facet>
														<center>
															<h:outputText styleClass="inputtext"
															value="#{list.ex_dt}" />
														</center>
													</rich:column>
                                           <rich:column align="center">
							<f:facet name="header">
								<h:outputText value="View"
									
									styleClass="generalHeaderOutputTable"></h:outputText>
							   </f:facet>
							   <h:commandButton value="View Details" 
							   actionListener="#{eoi_app_expo_report_action.view1}" 
							   styleClass="btn btn-primary btn-sm"/>
						       </rich:column>
								 <f:facet name="footer">
									 <rich:datascroller></rich:datascroller>
													</f:facet>
										</rich:dataTable>
								</div></h:panelGroup>
								
								<rich:spacer height="40px"></rich:spacer>
				<h:panelGroup rendered="#{eoi_app_expo_report_action.flag2==true}">
								<div align="center">
								<rich:spacer height='20px'></rich:spacer>
								
								<div align="center" style="background-color:#F4F3CC;">
				  Export Order  No. :-<h:outputText value="#{eoi_app_expo_report_action.export_no}" 
				  style="color:blue;"> </h:outputText>
				  
				  <rich:spacer width='30px'></rich:spacer>
				   Export Order Date. :-  <h:outputText value="#{eoi_app_expo_report_action.export_date}"
				  style="color:blue;"> </h:outputText>
				</div>
					<rich:spacer height='20px'></rich:spacer>			
			<rich:dataTable columnClasses="columnClass1"
													headerClass="TableHead" footerClass="TableHead"
													rowClasses="TableRow1,TableRow2" styleClass="table table-hover"
													width="90%" rows="10"
													value="#{eoi_app_expo_report_action.displaylist3}" var="list">
                                                    <rich:column>
														<f:facet name="header">
															<h:outputLabel value="Sr.NO"
																styleClass="generalHeaderStyle" style="FONT-SIZE: small;"/>
														</f:facet>
														<center>
															<h:outputText styleClass="generalExciseStyle"
															  value="#{list.sro}" />
														</center>
													</rich:column>
													<rich:column>
														<f:facet name="header">
															<h:outputLabel value="Export Order No"
																styleClass="generalHeaderStyle" style="FONT-SIZE: small;"/>
														</f:facet>
														<center>
															<h:outputText styleClass="generalExciseStyle"
															  value="#{list.export_no}" />
														</center>
													</rich:column>
													<rich:column>
														<f:facet name="header">
															<h:outputLabel value="FLB11 Number"
																styleClass="generalHeaderStyle" style="FONT-SIZE: small;"/>
														</f:facet>
														<center>
															<h:outputText styleClass="generalExciseStyle"
															  value="#{list.flb_no}" />
														</center>
													</rich:column>

													<rich:column>
														<f:facet name="header">
															<h:outputLabel value="FLB11 Date"
																styleClass="generalHeaderStyle" style="FONT-SIZE: small;"/>
														</f:facet>
														<center>
															<h:outputText styleClass="inputtext"
															value="#{list.flb1_dt}" />
														</center>
													</rich:column>
													<rich:column>
														<f:facet name="header">
															<h:outputLabel value="Shipping Bill No"
																styleClass="generalHeaderStyle" style="FONT-SIZE: small;"/>
														</f:facet>
														<center>
															<h:outputText styleClass="inputtext"
															  value="#{list.shipping_no}" />
														</center>
													</rich:column>
													
													<rich:column>
														<f:facet name="header">
															<h:outputLabel value="Bond Received Date"
																styleClass="generalHeaderStyle" style="FONT-SIZE: small;"/>
														</f:facet>
														<center>
															<h:outputText styleClass="inputtext"
															  value="#{list.bond_recdt}" />
														</center>
													</rich:column>
													
													<rich:column>
														<f:facet name="header">
															<h:outputLabel value="BRC No."
																styleClass="generalHeaderStyle" style="FONT-SIZE: small;"/>
														</f:facet>
														<center>
															<h:outputText styleClass="inputtext"
															  value="#{list.brc_num}" />
														</center>
													</rich:column>
													
													<rich:column>
														<f:facet name="header">
															<h:outputLabel value="BRC date"
																styleClass="generalHeaderStyle" style="FONT-SIZE: small;"/>
														</f:facet>
														<center>
															<h:outputText styleClass="inputtext"
															  value="#{list.brcc_dt}" />
														</center>
													</rich:column>
                                            <f:facet name="footer">
									 <rich:datascroller></rich:datascroller>
													</f:facet>
										</rich:dataTable>
								</div></h:panelGroup>
		
		</h:form>
	</f:view>
</ui:composition>