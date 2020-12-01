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
							<h:outputText value="Gatepass Search"
								styleClass="generalHeaderOutputTable"
								style="font-weight: bold; font-size: 40px;text-decoration: underline;margin-top: 2px;font-family:Monotype Corsiva;font-size:40px;" />
						</h2>
					</div>
				</div>

				<div class="row " align="center">
					<rich:spacer height="30px"></rich:spacer>
				</div>
				
					<rich:spacer height="20px" style="BACKGROUND-COLOR: #dee0e2;"/>
				
					
					<div class="col-md-12" align="center">
					
					<h:selectOneRadio style="FONT-WEIGHT: bold;  font-size: 16px; width: 60% "
						onclick="this.form.submit();"
						value="#{gatepass_Search_Action.vch}"
							onchange="this.form.submit()"  
							valueChangeListener="#{gatepass_Search_Action.radioListener}" >
							
					
					<f:selectItem itemValue="D" itemLabel="Distillery" />
					
					
					
					<f:selectItem itemValue="B" itemLabel="Brewery" />
				
					
					
					<f:selectItem itemValue="BWFL" itemLabel="BWFL" /> 
					
					
				
					<f:selectItem itemValue="FL2D" itemLabel="FL2D" />
					
						
					
					<f:selectItem itemValue="WS" itemLabel="WholeSale" />
				
					</h:selectOneRadio>



					</div>


			<div class="row"  >
					<rich:spacer height="20px"></rich:spacer>
				</div>
				
<rich:separator  height="3px" lineType="solid"></rich:separator>
				<div class="row"  >
					<rich:spacer height="20px"></rich:spacer>
				</div>


							<div align="center">
						
							
 	 															
 	 															

</div>
<div class="row col-md-12" >
					<div class="row col-md-2" >
					
					
					</div>
				
 				
					<div class="row col-md-2" align="right">
					
					</div>
				
					<div class="row col-md-2" align="right">
					
					<h:outputLabel 
							rendered="#{gatepass_Search_Action.vch eq 'D'}"
							value="Select License Type :" style="font-weight: bold">
							</h:outputLabel>
							<h:outputLabel 
							rendered="#{gatepass_Search_Action.vch eq 'B'}"
							value="Select License Type :" style="font-weight: bold">
							</h:outputLabel>
							<h:outputLabel 
							rendered="#{gatepass_Search_Action.vch eq 'WS'}"
							value="Select License Type :" style="font-weight: bold">
							</h:outputLabel>
					</div>
					
					<div class="row col-md-2" align="left">
					
						<h:selectOneMenu value="#{gatepass_Search_Action.licenceType}" 
									rendered="#{gatepass_Search_Action.vch eq 'D'}"
									>
 										<f:selectItem itemValue="" itemLabel="-- Select --"/>
 										<f:selectItem itemValue="FL3" itemLabel="FL3" />
 										<f:selectItem itemValue="FL3A" itemLabel="FL3A" />
 										<f:selectItem itemValue="CL" itemLabel="CL" />
 								</h:selectOneMenu>
 								
 						
						<h:selectOneMenu value="#{gatepass_Search_Action.licenceType}" 
									rendered="#{gatepass_Search_Action.vch eq 'B'}"
									>
 										<f:selectItem itemValue="" itemLabel="-- Select --"/>
 										<f:selectItem itemValue="FL3" itemLabel="FL3" />
 										<f:selectItem itemValue="FL3A" itemLabel="FL3A" />
 										
 								</h:selectOneMenu>
 								
 								
						<h:selectOneMenu value="#{gatepass_Search_Action.licenceType}" 
									rendered="#{gatepass_Search_Action.vch eq 'WS'}"
									>
 										
 										<f:selectItem itemValue="CL2" itemLabel="CL2" />
 									
 										
 								</h:selectOneMenu>
					</div>
					
					<div class="row col-md-2" align="left">
					
					</div>
					<div class="row col-md-2" align="center"></div>
					
						</div>
						
						
						
<div class="row"  >
					<rich:spacer height="20px"></rich:spacer>
				</div>


				<div class="row col-md-12" >
					<div class="row col-md-2" >
					
					
					</div>
				
 				
					<div class="row col-md-2" align="right">
					<h:outputLabel value="Gatepass No:" style="font-weight: bold" ></h:outputLabel>
					</div>
				
					<div class="row col-md-2" align="left">
					
					<h:inputText value="#{gatepass_Search_Action.gatepassNo}"></h:inputText>
					</div>
					
					<div class="row col-md-2" align="right">
					<h:outputLabel value="Gatepass Date:" style="font-weight: bold" ></h:outputLabel>
					
					</div>
					
					<div class="row col-md-2" align="left">
					 <rich:calendar
							value="#{gatepass_Search_Action.gatepass_date}" />
					</div>
					<div class="row col-md-2" align="center"></div>
					
						</div>
					
					
 					
 					<div class="row" align="center" >
 					<h:commandButton value="Search Gatepass" 
					class="btn btn-primary" 
					action="#{gatepass_Search_Action.getData}"> 
					</h:commandButton> 
					<h:commandButton value="Reset" 
					class="btn btn-danger" 
					action="#{gatepass_Search_Action.reset}"> 
					</h:commandButton>
						</div>
						
						
 				<div class="row"  >
					<rich:spacer height="20px"></rich:spacer>
				</div>
				
		<h:panelGroup rendered="#{gatepass_Search_Action.flag}">
				<TABLE width="80%" align="center"
												style="background-color: #D0D3D4; padding: 5;">
												<TBODY align="center">
													<tr>
														<td><rich:spacer height="10px;"></rich:spacer></td>
													</tr>
													<tr>
														<TD colspan="2" align="center"
														style="FONT-SIZE: small; FONT-WEIGHT: bold;"
														>Gatepass No : <h:inputText
																disabled="true"
																value="#{gatepass_Search_Action.vch_gatepass_no}" style=" width : 222px;"/>
														</TD>
													
														<TD colspan="2" align="center"
														style="FONT-SIZE: small; FONT-WEIGHT: bold;"
														>Date : <rich:calendar
																disabled="true"
																value="#{gatepass_Search_Action.dt_date}" />
														</TD>
														
													</tr>
													<tr>
														<TD><rich:spacer height="10px"></rich:spacer></TD>
													</tr>
													<TR></TR>


													<tr align="center">
														<TD align="right"
															style="FONT-WEIGHT: bold; padding-right: 10px;"><h:outputText
																value="From"
																style="FONT-SIZE: small; FONT-WEIGHT: bold;"></h:outputText>
														</TD>
														<TD align="left"><h:inputText
																value="#{gatepass_Search_Action.vch_from}"
																	disabled="true"
																></h:inputText>
																</TD>

														<td align="right" style="padding-right: 10px;"><h:outputText
																value="License No."
																style="FONT-SIZE: small; FONT-WEIGHT: bold;"></h:outputText>
														</td>
														<td align="left"><h:inputText
																value="#{gatepass_Search_Action.vch_from_lic_no}"
																	disabled="true"
																style="width: 250px; height: 28px;"/>
																
															</td>
													</tr>
													<tr align="center">
														<td style="padding-top: 10px;"></td>
													</tr>
													<tr align="center">
														<TD align="right"
															style="FONT-WEIGHT: bold; padding-right: 10px;"><h:outputText
																value="To" style="FONT-SIZE: small; FONT-WEIGHT: bold;"></h:outputText>
														</TD>
														<TD align="left"><h:inputText
																value="#{gatepass_Search_Action.vch_to}" 
																	disabled="true"></h:inputText>
																

															</TD>


														<td align="right" style="padding-right: 10px;"><h:outputText
																value="License No."
																style="FONT-SIZE: small; FONT-WEIGHT: bold;">
																</h:outputText>

														</td>
														<td align="left"><h:inputText
															
															
																value="#{gatepass_Search_Action.vch_to_lic_no}"
																	disabled="true"
																styleClass="generalHeaderOutputTable"
																style="width:250px">

															</h:inputText>
															</td>

															


													</tr>


													<tr align="center">
														<td style="padding-top: 10px;"></td>
													</tr>
													<tr style="padding-bottom: 10px;">
														<TD align="right"
															style="FONT-WEIGHT: bold; padding-right: 10px;"><h:outputText
																value="Licensee Name"
																style="FONT-SIZE: small; FONT-WEIGHT: bold;"></h:outputText>
														</TD>
														<td align="left"><h:inputText
																value="#{gatepass_Search_Action.licenceenm}"
																styleClass="generalHeaderOutputTable" disabled="true"		
																style="width:250px">

															</h:inputText></td>
														<TD align="right" style="padding-right: 10px;"><h:outputText
																value="Address"
																style="FONT-SIZE: small; FONT-WEIGHT: bold;"></h:outputText>
														</TD>
														<td align="left"><h:inputTextarea disabled="true"	
																value="#{gatepass_Search_Action.licenceeadd}"
																styleClass="generalHeaderOutputTable"
																style="width: 250px; ">

															</h:inputTextarea></td>
													</tr>

													<tr align="center">
														<td style="padding-top: 10px;"></td>
													</tr>

													<tr style="padding-bottom: 10px;">
														<TD align="right"
															style="FONT-WEIGHT: bold; padding-right: 10px;"><h:outputText
																value="Route Details"
																style="FONT-SIZE: small; FONT-WEIGHT: bold;"></h:outputText>
														</TD>
														<td align="left"><h:inputTextarea
																value="#{gatepass_Search_Action.routeDtl}"
																styleClass="generalHeaderOutputTable"
																	disabled="true"
																style="width: 250px; height : 63px;">

															</h:inputTextarea></td>
														<TD align="right" style="padding-right: 10px;"><h:outputText
																value="Vehicle No."
																style="FONT-SIZE: small; FONT-WEIGHT: bold;"></h:outputText>
														</TD>
														<td align="left"><h:inputText
																value="#{gatepass_Search_Action.vehicleNo}"
																	disabled="true"
																styleClass="generalHeaderOutputTable"
																style="width:250px">

															</h:inputText></td>
													</tr>

													<tr align="center">
														<td style="padding-top: 10px;"></td>
													</tr>

													<tr style="padding-bottom: 10px;">
														<TD align="right"
															style="FONT-WEIGHT: bold; padding-right: 10px;"><h:outputText
																value="Vehicle Driver Name"
																style="FONT-SIZE: small; FONT-WEIGHT: bold;"></h:outputText>
														</TD>
														<td align="left"><h:inputText
																value="#{gatepass_Search_Action.vehicleDrvrName}"
																	disabled="true"
																styleClass="generalHeaderOutputTable"
																style="width:250px">

															</h:inputText></td>
														<TD align="right" style="padding-right: 10px;"><h:outputText
																value="Vehicle Agency Name And Address"
																style="FONT-SIZE: small; FONT-WEIGHT: bold;"></h:outputText>
														</TD>
														<td align="left"><h:inputTextarea 
															disabled="true"
																value="#{gatepass_Search_Action.vehicleAgencyNmAdrs}"
																styleClass="generalHeaderOutputTable"
																style="width: 250px; height : 63px;">

															</h:inputTextarea></td>
													</tr>

													<tr align="center">
														<td style="padding-top: 10px;"></td>
													</tr>

												
													
													
													<tr>
														<td colspan="4"
															style="padding-top: 10px; padding-bottom: 10px;"></td>
													</tr>

												</TBODY>
											</TABLE>
				
				</h:panelGroup>
				
				
				<div class="row"  >
					<rich:spacer height="20px"></rich:spacer>
				</div>
				

				<h:panelGroup rendered="#{gatepass_Search_Action.flag}">
						<rich:dataTable align="center" id="table57" rows="10" width="100%"
							var="listk"
							
							value="#{gatepass_Search_Action.getVal}"
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
									<h:outputLabel value="Package" />
								</f:facet>
								<center>
									<h:outputText value="#{listk.package_name}" />
								</center>
							</rich:column>
									<rich:column>
								<f:facet name="header">
									<h:outputLabel value="Dispatch Box" />
								</f:facet>
								<center>
									<h:outputText value="#{listk.dispatch_box}" />
								</center>
							</rich:column>
								<rich:column>
								<f:facet name="header">
									<h:outputLabel value="Dispatch Bottles" />
								</f:facet>
								<center>
									<h:outputText value="#{listk.dispatch_bottle}" />
								</center>
							</rich:column>
							
								
							

							<f:facet name="footer">
								<rich:datascroller for="table57" />
							</f:facet>
						</rich:dataTable>
						
						
						
				
				<div class="row " align="center">
					<rich:spacer height="20px"></rich:spacer>
				</div>
				
				
			<div class="row col-md-12" >
					<div class="row col-md-2" >
					
					
					</div>
					<div class="row col-md-2" >
					
					
					</div>
				<div class="row col-md-2" >
					
					
					</div>
					<div class="row col-md-2" >
					
					
					</div>
					<div class="row col-md-2" >
						<h:commandButton value="View Uploaded CaseCode" 
					class="btn btn-success" 
					action="#{gatepass_Search_Action.viewCaseCode}"> 
					</h:commandButton> 
					
					</div>
 				
					
						</div>
						
				<div class="row"  >
					<rich:spacer height="20px"></rich:spacer>
				</div>
						
						
						
					</h:panelGroup>
			 
				<rich:dataTable align="center" id="table577" rows="10" width="100%"
							var="listk"
							rendered="#{gatepass_Search_Action.getCaseCodeFlag}"
							value="#{gatepass_Search_Action.getCaseCode}"
							headerClass="TableHead" footerClass="TableHead"
							rowClasses="TableRow1,TableRow2">

							<rich:column>
								<f:facet name="header">
									<h:outputLabel value="Sno" />
								</f:facet>
								<center>
									<h:outputText value="#{listk.snoK}" />
								</center>
							</rich:column>

							<rich:column>
								<f:facet name="header">
									<h:outputLabel value="CaseCode" />
								</f:facet>
								<center>
									<h:outputText value="#{listk.caseCode}" />
								</center>
							</rich:column>
                              	<rich:column>
								<f:facet name="header">
									<h:outputLabel value="Etin No" />
								</f:facet>
								<center>
									<h:outputText value="#{listk.etinNo}" />
								</center>
							</rich:column>
									<rich:column>
								<f:facet name="header">
									<h:outputLabel value="Plan Date" />
								</f:facet>
								<center>
									<h:outputText value="#{listk.plnDt}" />
								</center>
							</rich:column>
								
							
								
							

							<f:facet name="footer">
								<rich:datascroller for="table577" />
							</f:facet>
						</rich:dataTable>
				
			
				<br />
			</div>
		</h:form>
	</f:view>
</ui:composition>