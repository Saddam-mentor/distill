<ui:composition xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:a4j="http://richfaces.org/a4j"
	xmlns:rich="http://richfaces.org/rich">
	<f:view>
		<head>
<style>
.inputtext {
	border-radius: 6px;
	padding: 5px 5px;
	height: 30px;
	width: 100%;
	box-shadow: 1px 1px 15px lightsteelblue;
	border: 1px solid #669999;
}

.dropdown-menu {
	border-radius: 6px;
	padding: 5px 5px;
	height: 30px;
	width: 30%;
	box-shadow: 1px 1px 15px lightsteelblue;
	border: 1px solid #669999;
}

.dropdown-menu1 {
	border-radius: 6px;
	padding: 5px 5px;
	height: 30px;
	width: 75%;
	box-shadow: 1px 1px 15px lightsteelblue;
	border: 1px solid #669999;
}

.textarea1 {
	border-radius: 3px;
	border-style: none;
	width: 100%;
	box-shadow: 1px 1px 15px lightsteelblue;
	border: 1px solid #669999;
}
</style>
		</head>
		<h:form>
			<div class="container">
				<div class="row">
					<rich:spacer height="20px"></rich:spacer>
				</div>
				<div class="row">
					<div class="col-md-12 wow shake" align="center">
						<h:messages errorStyle="color:red" layout="TABLE" id="messages1"
							infoStyle="color:green"
							style="font-size:18px; background-color:#e1fcdf; font-weight: bold">
						</h:messages>

					</div>
				</div>

				<div class="row" align="center">
					<TABLE width="100%" align="center">
						<TR>
							<TD align="center" width="100%">
								<TABLE align="center" width="100%">
									<TBODY>

										<tr>
											<td><rich:separator lineType="dashed"></rich:separator>
											</td>
										</tr>

										<tr>
											<TD align="center" colspan="2"><h2>
													<h:outputText value="BWFl Permit Tracking"
														style="COLOR: #0000a0; FONT-WEIGHT: bold; FONT-SIZE: 35px;font-family:Comic Sans MS;">
													</h:outputText>
												</h2></TD>
										</tr>

										<tr>
											<td><rich:separator lineType="dashed"></rich:separator>
											</td>
										</tr>
									</TBODY>
								</TABLE>
							</TD>
						</TR>

					</TABLE>
				</div>
				<br />

				<h:panelGroup
					rendered="#{bwfl_permit_tracking_action.hideDataTable and !bwfl_permit_tracking_action.showMainPanel_flg}">
					
					<div class="row">
						<rich:spacer height="20px"></rich:spacer>
					</div>
					
					<TABLE width="100%" align="center">

									<TR>
										<TD align="center" width="100%">
											<TABLE align="center" width="100%">
												<TBODY>
													<TR align="center">



													</TR>
												</TBODY>
											</TABLE>
										</TD>
									</TR>
									<TR>
										<TD></TD>
									</TR>
									<TR>
										<TD></TD>
									</TR>
									<TR align="center">
										<TD colspan="1" align="center">
											<TABLE width="80%" align="center"
												style="background-color: #e6ffff; padding: 5;">
												<TBODY align="center">
													<tr>
														<td><rich:spacer height="10px;"></rich:spacer></td>
													</tr>

													<tr>
														<td><rich:spacer height="10px;"></rich:spacer></td>
													</tr>
													<tr>
														<TD colspan="2" align="center" style="COLOR: #2952a3; ; 
														 font-family:Times New Roman">Enter Permit Number : <h:inputText 
																value="#{bwfl_permit_tracking_action.permit_no}"
																style="width: 250px; height: 28px;">
															</h:inputText>

														</TD>

													</tr>

													<tr>
														<TD><rich:spacer height="10px"></rich:spacer></TD>
													</tr>
													<TR align="center">
														<td style="text-align: center;"><h:commandButton
																styleClass="btn btn-sm btn-primary" value="Fetch Details"
																action="#{bwfl_permit_tracking_action.viewDetails}">
															</h:commandButton>
															<rich:spacer width="10px"></rich:spacer>
																<h:commandButton styleClass="btn btn-sm btn-danger"
																	action="#{bwfl_permit_tracking_action.reset}" value="Reset"></h:commandButton></td>
													</TR>

													<tr>
														<td colspan="4"
															style="padding-top: 10px; padding-bottom: 10px;"></td>
													</tr>

												</TBODY> 
											</TABLE>
										</TD>
									</TR>
									<tr>
										<td colspan="4" align="center">
											<table width="90%" align="center">

											</table>
										</td>
									</tr>
									<TR>
										<TD></TD>
									</TR>
									<TR>
										<TD></TD>
									</TR>
									<tr align="center">
										<td colspan="4"></td>
									</tr>

								</TABLE>
					
					
					
				</h:panelGroup>
				<br />
				<div class="container-fuild">
					<h:panelGroup rendered="#{bwfl_permit_tracking_action.viewPanelFlg}">
						<div class="row" align="center">

							<rich:spacer height="20px" />
						</div>
						<div
							style="border: 1px solid black; padding-bottom: 10px; padding-top: 10px; padding-left: 10px; padding-right: 10px; margin-left: 5px; margin-right: 5px; border-radius: 4px;">
							<div class="row">
								<table align="center" width="100%;">
									<tr style="width: 100%">
										<td colspan="4"><h:outputLabel value="Basic Detail : "
												style="FONT-WEIGHT: bold; COLOR: #0000ff; FONT-SIZE: large; FONT-STYLE: italic;"></h:outputLabel>
										</td>
									</tr>
<tr>
										<td style="width: 100px; padding: 8px 16px;"><h:outputText
												value="Permit Number :" style="FONT-WEIGHT: bold;" /></td>
										<td style="width: 200px; padding: 8px 16px;"><h:inputTextarea
												disabled="true" styleClass="inputtext"
												value="#{bwfl_permit_tracking_action.permit_no}">
											</h:inputTextarea></td>
										<td
											style="width: 110px; text-align: center; padding: 8px 16px;"><h:outputText
												value="Permit Date :" style="FONT-WEIGHT: bold;" /></td>
										<td style="width: 200px; padding: 8px 16px;"><h:inputText
												styleClass="textarea1" disabled="true"
												value="#{bwfl_permit_tracking_action.permitDate}" style=" height : 28px;">
											</h:inputText></td>

									</tr> 
									<tr>
										<td style="width: 100px; padding: 8px 16px;"><h:outputText
												value="License Number :" style="FONT-WEIGHT: bold;" /></td>
										<td style="width: 200px; padding: 8px 16px;"><h:inputText
												disabled="true" styleClass="inputtext"
												value="#{bwfl_permit_tracking_action.licenseNmbr}">
											</h:inputText></td>
										<td
											style="width: 110px; text-align: center; padding: 8px 16px;"><h:outputText
												value="Transport:" style="FONT-WEIGHT: bold;" /></td>
										<td style="width: 200px; padding: 8px 16px;"><h:selectOneRadio
												style="width:80%" disabled="true"
												value="#{bwfl_permit_tracking_action.byRoad_byRoute}">
												<f:selectItem itemValue="route" itemLabel="By Train" />
												<f:selectItem itemValue="road" itemLabel="By Road" />
											</h:selectOneRadio></td>
										<td
											style="width: 110px; text-align: center; padding: 8px 16px;"><h:outputText
												value="Route Details :" style="FONT-WEIGHT: bold;" /></td>
										<td style="width: 200px; padding: 8px 16px;"><h:inputTextarea
												styleClass="textarea1" disabled="true"
												value="#{bwfl_permit_tracking_action.routeDetail}">
											</h:inputTextarea></td>

									</tr>
									<tr>
										<rich:spacer height="5px;"></rich:spacer>
									</tr>

									<tr>



										<td style="width: 100px; padding: 8px 16px;"><h:outputText
												value="Parent Unit Name:" style="FONT-WEIGHT: bold;"
												rendered="#{!bwfl_permit_tracking_action.fl2dDutyFlg}" /></td>
										<td style="width: 200px; padding: 8px 16px;"><h:inputTextarea
												styleClass="textarea1" disabled="true"
												value="#{bwfl_permit_tracking_action.parentUnitNm}"
												rendered="#{!bwfl_permit_tracking_action.fl2dDutyFlg}">
											</h:inputTextarea></td>

										<td
											style="width: 110px; text-align: center; padding: 8px 16px;"><h:outputText
												value="Parent Unit Address:" style="FONT-WEIGHT: bold;"
												rendered="#{!bwfl_permit_tracking_action.fl2dDutyFlg}" /></td>

										<td style="width: 200px; padding: 8px 16px;"><h:inputTextarea
												styleClass="textarea1" disabled="true"
												value="#{bwfl_permit_tracking_action.parentUnitAdrs}"
												rendered="#{!bwfl_permit_tracking_action.fl2dDutyFlg}">

											</h:inputTextarea></td>

										<td
											style="width: 110px; text-align: center; padding: 8px 16px;"><h:outputText
												value="Parent Unit State:" style="FONT-WEIGHT: bold;"
												rendered="#{!bwfl_permit_tracking_action.fl2dDutyFlg}" /></td>

										<td style="width: 200px; padding: 8px 16px;"><h:inputText
												disabled="true" styleClass="inputtext"
												value="#{bwfl_permit_tracking_action.parentUnitState}"
												rendered="#{!bwfl_permit_tracking_action.fl2dDutyFlg}"></h:inputText></td>


									</tr>

									<tr>
										<rich:spacer height="5px;"></rich:spacer>
									</tr>

									<tr>
										<td colspan="2" style="padding: 8px 16px;"><h:outputText
												value="State of Unit from where Import is to be done :"
												style="FONT-WEIGHT: bold;"
												rendered="#{bwfl_permit_tracking_action.fl2dDutyFlg}" /></td>
										<td style="width: 200px; padding: 8px 16px;"><h:inputText
												disabled="true" styleClass="inputtext"
												value="#{bwfl_permit_tracking_action.parentUnitState}"
												rendered="#{bwfl_permit_tracking_action.fl2dDutyFlg}">
											</h:inputText></td>
										<td style="width: 100px; padding: 8px 16px;"><h:outputText
												value="Name and Address of Unit from where Import is to be done :"
												style="FONT-WEIGHT: bold;"
												rendered="#{bwfl_permit_tracking_action.fl2dDutyFlg}" /></td>
										<td colspan="2" style="padding: 8px 16px;"><h:inputTextarea
												styleClass="textarea1" disabled="true"
												value="#{bwfl_permit_tracking_action.consignorNmAdrs}"
												rendered="#{bwfl_permit_tracking_action.fl2dDutyFlg}" /></td>

									</tr>

									<tr>
										<rich:spacer height="5px;"></rich:spacer>
									</tr>

									<tr>
										<td colspan="2" style="padding: 8px 16px;"><h:outputText
												value="QR Code / Bar Code Requested As :"
												rendered="#{!bwfl_permit_tracking_action.fl2dDutyFlg}"
												style="FONT-WEIGHT: bold;" /></td>
										<td style="width: 200px; padding: 8px 16px;"><h:selectOneRadio
												style="width:80%" disabled="true"
												rendered="#{!bwfl_permit_tracking_action.fl2dDutyFlg}"
												value="#{bwfl_permit_tracking_action.mapped_unmapped}">
												<f:selectItem itemValue="M" itemLabel="Mapped" />
												<f:selectItem itemValue="U" itemLabel="UnMapped" />
											</h:selectOneRadio></td>

									</tr>
									<tr>
										<rich:spacer height="5px;"></rich:spacer>
									</tr>

								</table>
								<div class="row" align="center">
									<rich:spacer height="10px"></rich:spacer>
								</div>
								<div class="row">
									<div class="col-md-12">
										<h:outputLabel value="Permit Required For : "
											style="FONT-WEIGHT: bold; COLOR: #0000ff; FONT-SIZE: large; FONT-STYLE: italic;"></h:outputLabel>
									</div>

									<div class="row" align="center">
										<div class="col-md-12">
											<rich:dataTable id="table2p" width="100%"
												value="#{bwfl_permit_tracking_action.displayBrandDetails}"
												var="list" headerClass="TableHead" footerClass="TableHead"
												styleClass="DataTable" rowClasses="TableRow1,TableRow2">


												<rich:column>
													<f:facet name="header">
														<h:outputText value="Sr.No">
														</h:outputText>
													</f:facet>
													<h:outputText style="margin-left: 20px;"
														value="#{list.srNo}"></h:outputText>
													<f:facet name="footer">
														<h:outputText value="Total"
															styleClass="generalHeaderOutputTable" />
													</f:facet>
												</rich:column>

												<rich:column>
													<f:facet name="header">
														<h:outputText value="Brand Name" />
													</f:facet>
													<center>
														<h:outputText styleClass="generalExciseStyle"
															value="#{list.brandName_dt}" />
													</center>
													<f:facet name="footer">
														<h:outputText value=""
															styleClass="generalHeaderOutputTable" />
													</f:facet>
												</rich:column>



												<rich:column>
													<f:facet name="header">
														<h:outputText value="Package Name" />
													</f:facet>
													<center>
														<h:outputText styleClass="generalExciseStyle"
															value="#{list.pckgType_dt}" />
													</center>
													<f:facet name="footer">
														<h:outputText value=""
															styleClass="generalHeaderOutputTable" />
													</f:facet>
												</rich:column>

												<rich:column>
													<f:facet name="header">
														<h:outputText value="ETIN">
														</h:outputText>
													</f:facet>
													<h:outputText styleClass="generalExciseStyle"
														value="#{list.etinNo_dt}">
													</h:outputText>
													<f:facet name="footer">
														<h:outputText value=""
															styleClass="generalHeaderOutputTable" />
													</f:facet>
												</rich:column>
												<rich:column>
													<f:facet name="header">
														<h:outputText value="No.of Bottles Per case" />
													</f:facet>
													<center>
														<h:outputText styleClass="generalExciseStyle"
															value="#{list.size_dt}" />
													</center>
													<f:facet name="footer">
														<h:outputText value=""
															styleClass="generalHeaderOutputTable" />
													</f:facet>
												</rich:column>
												<rich:column>
													<f:facet name="header">
														<h:outputText value="No.of Boxes" />
													</f:facet>
													<center>
														<h:outputText styleClass="generalExciseStyle"
															value="#{list.nmbrOfBox_dt}" />
													</center>
													<f:facet name="footer">
														<h:outputText value=""
															styleClass="generalHeaderOutputTable" />
													</f:facet>
												</rich:column>



												<rich:column>
													<f:facet name="header">
														<h:outputText value="No.of Bottles" />
													</f:facet>
													<center>
														<h:outputText styleClass="generalExciseStyle"
															value="#{list.nmbrOfBtl_dt}" />
													</center>
													<f:facet name="footer">
														<h:outputText value=""
															styleClass="generalHeaderOutputTable" />
													</f:facet>
												</rich:column>

												<rich:column rendered="#{!bwfl_permit_tracking_action.fl2dDutyFlg}">
													<f:facet name="header">
														<h:outputText value="Duty" />
													</f:facet>
													<center>
														<h:outputText styleClass="generalExciseStyle"
															value="#{list.duty_dt}">
															<f:convertNumber maxFractionDigits="2"
																pattern="#############0.00" />
														</h:outputText>
													</center>
													<f:facet name="footer">
														<h:outputText id="totalduty"
															value="#{bwfl_permit_tracking_action.totalDuty}"
															styleClass="generalHeaderOutputTable">
															<f:convertNumber maxFractionDigits="2"
																pattern="#############0.00" />
														</h:outputText>
													</f:facet>
												</rich:column>



												<rich:column>
													<f:facet name="header">
														<h:outputText value="Import Fee" />
													</f:facet>
													<center>
														<h:outputText styleClass="generalExciseStyle"
															value="#{list.importFees_dt}">
															<f:convertNumber maxFractionDigits="2"
																pattern="#############0.00" />
														</h:outputText>
													</center>
													<f:facet name="footer">
														<h:outputText id="totalfees"
															value="#{bwfl_permit_tracking_action.totalImportFee}"
															styleClass="generalHeaderOutputTable">
															<f:convertNumber maxFractionDigits="2"
																pattern="#############0.00" />
														</h:outputText>
													</f:facet>
												</rich:column>

												<rich:column>
													<f:facet name="header">
														<h:outputText value="Scanning Fee" />
													</f:facet>
													<center>
														<h:outputText styleClass="generalExciseStyle"
															value="#{list.scanning_fee}">
															<f:convertNumber maxFractionDigits="2"
																pattern="#############0.00" />
														</h:outputText>
													</center>
													<f:facet name="footer">
														<h:outputText id="totscanfees"
															value="#{bwfl_permit_tracking_action.total_scanning_fee}"
															styleClass="generalHeaderOutputTable">
															<f:convertNumber maxFractionDigits="2"
																pattern="#############0.00" />
														</h:outputText>
													</f:facet>
												</rich:column>
												
												<rich:column>
													<f:facet name="header">
														<h:outputText value="Special Fee" />
													</f:facet>
													<center>
														<h:outputText styleClass="generalExciseStyle"
															value="#{list.specialfees_dt}">
															<f:convertNumber maxFractionDigits="2"
																pattern="#############0.00" />
														</h:outputText>
													</center>
													<f:facet name="footer">
														<h:outputText id="totspclfees"
															value="#{bwfl_permit_tracking_action.totalSpecialFee}"
															styleClass="generalHeaderOutputTable">
															<f:convertNumber maxFractionDigits="2"
																pattern="#############0.00" />
														</h:outputText>
													</f:facet>
												</rich:column>

											</rich:dataTable>

										</div>
									</div>

								</div>
								<div class="row">
									<rich:spacer height="10px"></rich:spacer>
								</div>



							</div>
						</div>
					</h:panelGroup>

				</div>
				<div class="row" align="center">
					<rich:spacer height="10px"></rich:spacer>
				</div>



				<h:panelGroup
					rendered="#{bwfl_permit_tracking_action.viewPanelFlg}">

					<div class="row" style="BACKGROUND-COLOR: #e6ffff;">

						<div class="row" align="center">
							<rich:spacer height="10px"></rich:spacer>
						</div>
						<div class="col-md-12" align="center">
							<h:outputLabel value="CHALLAN DETAILS : "
								style="FONT-WEIGHT: bold; COLOR: #003399; FONT-SIZE: large; FONT-STYLE: italic;"></h:outputLabel>
						</div>
						<div class="row" align="center">
							<rich:spacer height="10px"></rich:spacer>
						</div>

						<div class="row" align="center">
							<div class="col-md-12">
								<rich:dataTable id="table3" width="100%"
									value="#{bwfl_permit_tracking_action.displayChalanDetails}" var="list"
									headerClass="TableHead" footerClass="TableHead"
									styleClass="DataTable" rowClasses="TableRow1,TableRow2">


									<rich:column>
										<f:facet name="header">
											<h:outputText value="Sr.No">
											</h:outputText>
										</f:facet>
										<h:outputText style="margin-left: 20px;" value="#{list.srNo}"></h:outputText>
										<f:facet name="footer">
											<h:outputText value="" styleClass="generalHeaderOutputTable" />
										</f:facet>
									</rich:column>

									<rich:column>
										<f:facet name="header">
											<h:outputText value="Challan No." />
										</f:facet>
										<center>
											<h:outputText styleClass="generalExciseStyle"
												value="#{list.challanNo_dt}" />
										</center>
										<f:facet name="footer">
											<h:outputText value="" styleClass="generalHeaderOutputTable" />
										</f:facet>
									</rich:column>



									<rich:column>
										<f:facet name="header">
											<h:outputText value="Challan Date" />
										</f:facet>
										<center>
											<h:outputText styleClass="generalExciseStyle"
												value="#{list.challanDate_dt}" />
										</center>
										<f:facet name="footer">
											<h:outputText value="" styleClass="generalHeaderOutputTable" />
										</f:facet>
									</rich:column>

									<rich:column>
										<f:facet name="header">
											<h:outputText value="Challan Amount">
											</h:outputText>
										</f:facet>
										<h:outputText styleClass="generalExciseStyle"
											value="#{list.challanAmnt_dt}">
											<f:convertNumber maxFractionDigits="2"
												pattern="#############0.00" />

										</h:outputText>
										<f:facet name="footer">
											<h:outputText value="" styleClass="generalHeaderOutputTable" />
										</f:facet>
									</rich:column>
									<rich:column>
										<f:facet name="header">
											<h:outputText value="Division Name" />
										</f:facet>
										<center>
											<h:outputText styleClass="generalExciseStyle"
												value="#{list.divName_dt}" />
										</center>
										<f:facet name="footer">
											<h:outputText value="" styleClass="generalHeaderOutputTable" />
										</f:facet>
									</rich:column>
									<rich:column>
										<f:facet name="header">
											<h:outputText value="Treasury Name" />
										</f:facet>
										<center>
											<h:outputText styleClass="generalExciseStyle"
												value="#{list.treasryNm_dt}" />
										</center>
										<f:facet name="footer">
											<h:outputText value="" styleClass="generalHeaderOutputTable" />
										</f:facet>
									</rich:column>

								</rich:dataTable>

							</div>
						</div>
                         <div class="col-md-12" style="height: 15px"></div>
					</div>
					<div align="center">
					
					<h:commandButton styleClass="btn btn-sm btn-info"
					 action="#{bwfl_permit_tracking_action.close}" value="Close"></h:commandButton>
					</div>
					
				</h:panelGroup>
				<div class="row" align="center">
					<rich:spacer height="10px"></rich:spacer>
				</div>
		</div>
</h:form>


	</f:view>
</ui:composition>