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
													<h:outputText value="Brand Label Applications Tracking"
														style="COLOR: #0000a0; FONT-WEIGHT: bold; FONT-SIZE: 35px;font-family:Monotype Corsiva;">
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
					rendered="#{brand_label_tracking_action.tableflag}">
					
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
														 font-family:Times New Roman">Enter Approval Order Number : <h:inputText 
																value="#{brand_label_tracking_action.order_no}"
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
																action="#{brand_label_tracking_action.viewDetails}">
															</h:commandButton>
															<rich:spacer width="10px"></rich:spacer>
																<h:commandButton styleClass="btn btn-sm btn-danger"
																	action="#{brand_label_tracking_action.reset}" value="Reset"></h:commandButton></td>
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
			
				<br /> <br />
				
				<div class="container-fuild">
					<h:panelGroup
						rendered="#{brand_label_tracking_action.panelflag}">
						
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
												value="Application Date :" style="FONT-WEIGHT: bold;" /></td>
										<td style="width: 200px; padding: 8px 16px;"><h:inputText
												disabled="true" styleClass="inputtext"
												value="#{brand_label_tracking_action.appDate}">
											</h:inputText></td>
											
										<td
										  style="width: 110px; text-align: center; padding: 8px 16px;"><h:outputText
												value="Order Number :" style="FONT-WEIGHT: bold;" /></td>

										<td style="width: 200px; padding: 8px 16px;"><h:inputTextarea
												styleClass="textarea1" disabled="true"
												value="#{brand_label_tracking_action.order_no}">

											</h:inputTextarea></td>
											
											<td
											style="width: 110px; text-align: center; padding: 8px 16px;"><h:outputText
												value="Order Date :" style="FONT-WEIGHT: bold;" /></td>

										<td style="width: 200px; padding: 8px 16px;"><h:inputText
												disabled="true" styleClass="inputtext"
												value="#{brand_label_tracking_action.order_date}"></h:inputText></td>
									</tr>
									<tr>
										<rich:spacer height="5px;"></rich:spacer>
									</tr>

									<tr>
										<td style="width: 100px; padding: 8px 16px;"><h:outputText
												value=" Unit Name :" style="FONT-WEIGHT: bold;" /></td>
										<td style="width: 200px; padding: 8px 16px;"><h:inputTextarea
												styleClass="textarea1" disabled="true"
												value="#{brand_label_tracking_action.unitName}">

											</h:inputTextarea></td>

										<td
											style="width: 110px; text-align: center; padding: 8px 16px;"><h:outputText
												value="Unit Address :" style="FONT-WEIGHT: bold;" /></td>

										<td style="width: 200px; padding: 8px 16px;"><h:inputTextarea
												styleClass="textarea1" disabled="true"
												value="#{brand_label_tracking_action.unitAddress}">

											</h:inputTextarea></td>

										<td
											style="width: 110px; text-align: center; padding: 8px 16px;"><h:outputText
												value="Unit Type :" style="FONT-WEIGHT: bold;" /></td>

										<td style="width: 200px; padding: 8px 16px;"><h:inputText
												disabled="true" styleClass="inputtext"
												value="#{brand_label_tracking_action.unitType}"></h:inputText></td>
									</tr>
									<tr>
										<rich:spacer height="5px;"></rich:spacer>
									</tr>

									<tr>
										<td colspan="6" align="center"></td>

									</tr>

									<tr>
										<rich:spacer height="5px;"></rich:spacer>
									</tr>






									<tr>
										<td style="width: 100px; padding: 8px 16px;"><h:outputText
												value="Liqour Type :" style="FONT-WEIGHT: bold;" /></td>
										<td style="width: 200px; padding: 8px 16px;"><h:inputText
												disabled="true" styleClass="inputtext"
												value="#{brand_label_tracking_action.liquorCategory}">
											</h:inputText></td>
										<td
											style="width: 110px; text-align: center; padding: 8px 16px;"><h:outputText
												value="License Type :" style="FONT-WEIGHT: bold;" /></td>
										<td style="width: 200px; padding: 8px 16px;"><h:inputText
												disabled="true" styleClass="inputtext"
												value="#{brand_label_tracking_action.licenseType}" /></td>
										<td
											style="width: 110px; text-align: center; padding: 8px 16px;"><h:outputText
												value="Domain :" style="FONT-WEIGHT: bold;" /></td>
										<td style="width: 200px; padding: 8px 16px;"><h:inputText
												disabled="true" styleClass="inputtext"
												value="#{brand_label_tracking_action.userDomain}"
												maxlength="10" /></td>

									</tr>

	<tr>
										<rich:spacer height="5px;"></rich:spacer>
									</tr>

                              <tr>
										<td style="width: 100px; padding: 8px 16px;"><h:outputText
												value="View Digitally Signed Order :" style="FONT-WEIGHT: bold;" /></td>
										<td style="width: 200px; padding: 8px 16px;"><h:outputLink
							            value="/doc/ExciseUp/Applications/pdf/#{brand_label_tracking_action.order_no_view}"
							             target="_blank">
							             <h:outputText value="view Permit"/>
						</h:outputLink></td>
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
										<h:outputLabel
											value="#{brand_label_tracking_action.brandtext}"
											style="color:green; FONT-WEIGHT: bold;   FONT-SIZE: large; FONT-STYLE: italic;"></h:outputLabel>


									</div>
								</div>
								<div class="row">
									<div class="col-md-12">
										<h:outputLabel rendered="#{brand_label_tracking_action.brnd_reg_in_20_21 eq 'YES'}"
											value="This Brand is already registered for 2020-21 and only label is to be changed/Added !"
											style="color:green; FONT-WEIGHT: bold;   FONT-SIZE: large; FONT-STYLE: italic;"></h:outputLabel>


									</div>
								</div>
								<div class="row" align="center">
									<rich:spacer height="10px"></rich:spacer>
								</div>
								<div class="row">
									<div class="col-md-12">
										<h:outputLabel value="Brand Challan Details : "
											style="TEXT-DECORATION: underline; FONT-WEIGHT: bold;   FONT-SIZE: large; FONT-STYLE: italic;"></h:outputLabel>
									</div>
									<div class="row" align="center">
										<rich:spacer height="10px"></rich:spacer>
									</div>
									<div class="row" align="center">
										<div class="col-md-12">




											<rich:dataTable id="table3p" width="100%"
												value="#{brand_label_tracking_action.brandChallaList}"
												var="list1" headerClass="TableHead" footerClass="TableHead"
												styleClass="DataTable" rowClasses="TableRow1,TableRow2"
												style=" height : 78px;">


												<rich:column>
													<f:facet name="header">
														<h:outputText value="Sr.No">
														</h:outputText>
													</f:facet>
													<h:outputText style="margin-left: 20px;"
														value="#{list1.brandSrNo}"></h:outputText>
													<f:facet name="footer">
														<h:outputText value=""
															styleClass="generalHeaderOutputTable" />
													</f:facet>
												</rich:column>
												<rich:column>
													<f:facet name="header">
														<h:outputText value="Challan Date">
														</h:outputText>
													</f:facet>
													<h:outputText styleClass="generalExciseStyle"
														value="#{list1.brandChallan_dt}">
													</h:outputText>
													<f:facet name="footer">
														<h:outputText value=""
															styleClass="generalHeaderOutputTable" />
													</f:facet>
												</rich:column>


												<rich:column>
													<f:facet name="header">
														<h:outputText value="Challan Id">
														</h:outputText>
													</f:facet>
													<h:outputText styleClass="generalExciseStyle"
														value="#{list1.brandChallan_id}">
													</h:outputText>
													<f:facet name="footer">
														<h:outputText value=""
															styleClass="generalHeaderOutputTable" />
													</f:facet>
												</rich:column>
												<rich:column>
													<f:facet name="header">
														<h:outputText value="Challan">
														</h:outputText>
													</f:facet>
													<h:outputLink value="#{list1.brandChallan_pdf}"
														target="_blank">
														<h:outputText styleClass="outputText" value="View pdf"
															style="color: blue; font-family: serif; font-size: 12px"></h:outputText>
													</h:outputLink>
													<f:facet name="footer">
														<h:outputText value=""
															styleClass="generalHeaderOutputTable" />
													</f:facet>
												</rich:column>
											</rich:dataTable>

										</div>
									</div>

									<rich:spacer height="30px">
									</rich:spacer>
									<div class="col-md-12">
										<h:outputLabel value="Brand and Package Details : "
											style="TEXT-DECORATION: underline; FONT-WEIGHT: bold;   FONT-SIZE: large; FONT-STYLE: italic;"></h:outputLabel>
									</div>
									<div class="row" align="center">
										<rich:spacer height="10px"></rich:spacer>
									</div>
									<div class="row">
										<h:outputText
											value="Total Brand Fee : #{brand_label_tracking_action.brndchallanfee}"
											style="COLOR: #000080; font-family: serif; font-size: 18px"></h:outputText>


									</div>
									<div class="row" align="center">
										<div class="col-md-12">
											<rich:dataTable id="table2p" width="100%"
												value="#{brand_label_tracking_action.displayLabelDetails}"
												var="list" headerClass="TableHead" footerClass="TableHead"
												styleClass="DataTable" rowClasses="TableRow1,TableRow2"
												style=" height : 78px;">


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
														<h:outputText value="Brand Name">
														</h:outputText>
													</f:facet>
													<h:outputText styleClass="generalExciseStyle"
														value="#{list.brandnam}">
													</h:outputText>
													<f:facet name="footer">
														<h:outputText value=""
															styleClass="generalHeaderOutputTable" />
													</f:facet>
												</rich:column>


												<rich:column>
													<f:facet name="header">
														<h:outputText value="Brand Strength">
														</h:outputText>
													</f:facet>
													<h:outputText styleClass="generalExciseStyle"
														value="#{list.brndstrength}">
													</h:outputText>
													<f:facet name="footer">
														<h:outputText value=""
															styleClass="generalHeaderOutputTable" />
													</f:facet>
												</rich:column>

												<rich:column>
													<f:facet name="header">
														<h:outputText value="For Civil/CSD">
														</h:outputText>
													</f:facet>
													<h:outputText styleClass="generalExciseStyle"
														value="#{list.forcivil}">
													</h:outputText>
													<f:facet name="footer">
														<h:outputText value=""
															styleClass="generalHeaderOutputTable" />
													</f:facet>
												</rich:column>


												<rich:column>
													<f:facet name="header">
														<h:outputText value="Liqour Sub Type">
														</h:outputText>
													</f:facet>

													<h:selectOneMenu styleClass="dropdown-menu" id="menu"
														disabled="#{brand_label_tracking_action.radioType ne 'DS' or brand_label_tracking_action.approvingFlg}"
														value="#{list.subtype}" style=" width : 130px;">
														<f:selectItem itemValue="NA" itemLabel="NA" />
														<f:selectItem itemValue="1" itemLabel="Economy" />
														<f:selectItem itemValue="2" itemLabel="Regular" />
														<f:selectItem itemValue="3" itemLabel="Medium" />
														<f:selectItem itemValue="4" itemLabel="Scotch" />
														<f:selectItem itemValue="6" itemLabel="Premium" />
														<f:selectItem itemValue="7" itemLabel="Super Premium" />
														<f:selectItem itemValue="8" itemLabel="Mild" />
														<f:selectItem itemValue="9" itemLabel="Strong" />
														<f:selectItem itemValue="10" itemLabel="Plain" />
														<f:selectItem itemValue="11" itemLabel="Masala" />
														<f:selectItem itemValue="14" itemLabel="Gim" />
														<f:selectItem itemValue="15" itemLabel="Vodka" />
														<f:selectItem itemValue="16" itemLabel="Wine" />
														<f:selectItem itemValue="17" itemLabel="Whisky" />
														<f:selectItem itemValue="18" itemLabel="Brandy" />
														<f:selectItem itemValue="19" itemLabel="Rum" />
														<f:selectItem itemValue="21" itemLabel="LAB" />
													</h:selectOneMenu>
													<h:commandButton
														action="#{brand_label_tracking_action.updatesubtype}"
														value="Update" styleClass="btn btn-success btn-sm"
														rendered="#{brand_label_tracking_action.radioType eq 'DS' and brand_label_tracking_action.paymentflgFlg}">

														<f:setPropertyActionListener value="#{list}"
															target="#{brand_label_tracking_action.dt }" />

													</h:commandButton>
													<f:facet name="footer">
														<h:outputText value=""
															styleClass="generalHeaderOutputTable" />
													</f:facet>
												</rich:column>


												<rich:column>
													<f:facet name="header">
														<h:outputText
															value="Whether this brand is already registered for Civil">
														</h:outputText>
													</f:facet>
													<h:outputText styleClass="generalExciseStyle"
														value="#{list.yesno}">
													</h:outputText>
													<f:facet name="footer">
														<h:outputText value=""
															styleClass="generalHeaderOutputTable" />
													</f:facet>
												</rich:column>


												<rich:column>
													<f:facet name="header">
														<h:outputText value="Size(ml)">
														</h:outputText>
													</f:facet>
													<h:outputText styleClass="generalExciseStyle"
														value="#{list.size_dt}">
													</h:outputText>
													<f:facet name="footer">
														<h:outputText value=""
															styleClass="generalHeaderOutputTable" />
													</f:facet>
												</rich:column>

												<rich:column>
													<f:facet name="header">
														<h:outputText value="Package Type" />
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
														<h:outputText value="Number of labels" />
													</f:facet>
													<center>
														<h:outputText styleClass="generalExciseStyle"
															value="#{list.nmbrOfLabels_dt}" />
													</center>
													<f:facet name="footer">
														<h:outputText id="totalnum"
															value="#{brand_label_tracking_action.total_no_labels}"
															styleClass="generalHeaderOutputTable" />
													</f:facet>
												</rich:column>

												<rich:column
													rendered="#{brand_label_tracking_action.feeflg ne 'T'}">
													<f:facet name="header">
														<h:outputText value="Label Fees(Rs)" />
													</f:facet>
													<center>
														<h:outputText styleClass="generalExciseStyle"
															value="#{list.feeslable}" />
													</center>
													<f:facet name="footer">

													</f:facet>
												</rich:column>
												<rich:column  rendered="#{brand_label_tracking_action.radioType eq 'N' and brand_label_tracking_action.delete_brand_pack eq 'T'}">
													<f:facet name="header">
														<h:outputText value="">
														</h:outputText>
													</f:facet>

													
													<h:commandButton
													onclick="return confirm('ALERT : This Brand will be deleted. Do you wish to continue?');"
														action="#{brand_label_tracking_action.delete_brand}"
														value="Delete Brand" styleClass="btn btn-danger btn-sm" >

														<f:setPropertyActionListener value="#{list}"
															target="#{brand_label_tracking_action.dt }" />

													</h:commandButton>
													<h:commandButton
													onclick="return confirm('ALERT : This Package will be deleted. Do you wish to continue?');"
														action="#{brand_label_tracking_action.delete_package}"
														value="Delete Package" styleClass="btn btn-danger btn-sm" >

														<f:setPropertyActionListener value="#{list}"
															target="#{brand_label_tracking_action.dt }" />

													</h:commandButton>
													<f:facet name="footer">
														<h:outputText value=""
															styleClass="generalHeaderOutputTable" />
													</f:facet>
												</rich:column>









											</rich:dataTable>

										</div>
									</div>

								</div>
								<div class="row">
									<rich:spacer height="10px"></rich:spacer>
								</div>
								<div class="col-md-12">
									<h:outputLabel value="Label Challan Details : "
										style="TEXT-DECORATION: underline; FONT-WEIGHT: bold;   FONT-SIZE: large; FONT-STYLE: italic;"></h:outputLabel>
								</div>
								<div class="row" align="center">
									<div class="col-md-12">




										<rich:dataTable id="table5p" width="100%"
											value="#{brand_label_tracking_action.labelChallanList}"
											var="list1" headerClass="TableHead" footerClass="TableHead"
											styleClass="DataTable" rowClasses="TableRow1,TableRow2"
											style=" height : 78px;">


											<rich:column>
												<f:facet name="header">
													<h:outputText value="Sr.No">
													</h:outputText>
												</f:facet>
												<h:outputText style="margin-left: 20px;"
													value="#{list1.labelSrNo}"></h:outputText>
												<f:facet name="footer">
													<h:outputText value=""
														styleClass="generalHeaderOutputTable" />
												</f:facet>
											</rich:column>
											<rich:column>
												<f:facet name="header">
													<h:outputText value="Challan Date">
													</h:outputText>
												</f:facet>
												<h:outputText styleClass="generalExciseStyle"
													value="#{list1.labelChallan_dt}">
												</h:outputText>
												<f:facet name="footer">
													<h:outputText value=""
														styleClass="generalHeaderOutputTable" />
												</f:facet>
											</rich:column>


											<rich:column>
												<f:facet name="header">
													<h:outputText value="Challan Id">
													</h:outputText>
												</f:facet>
												<h:outputText styleClass="generalExciseStyle"
													value="#{list1.labelChallan_id}">
												</h:outputText>
												<f:facet name="footer">
													<h:outputText value=""
														styleClass="generalHeaderOutputTable" />
												</f:facet>
											</rich:column>
											<rich:column>
												<f:facet name="header">
													<h:outputText value="Challan">
													</h:outputText>
												</f:facet>
												<h:outputLink value="#{list1.labelChallan_pdf}"
													target="_blank">
													<h:outputText styleClass="outputText" value="View pdf"
														style="color: blue; font-family: serif; font-size: 12px"></h:outputText>
												</h:outputLink>
												<f:facet name="footer">
													<h:outputText value=""
														styleClass="generalHeaderOutputTable" />
												</f:facet>
											</rich:column>
										</rich:dataTable>

									</div>
								</div>
								<div class="row" align="center">
									<rich:spacer height="10px"></rich:spacer>
								</div>

								<div class="row" align="center">
									<rich:spacer height="10px"></rich:spacer>
								</div>
								<div class="row">
									<div class="col-md-12">
										<h:outputLabel value="Uploaded Labels  "
											style="TEXT-DECORATION: underline; FONT-WEIGHT: bold;   FONT-SIZE: large; FONT-STYLE: italic;"></h:outputLabel>
									</div>
									<rich:spacer height="30px">
									</rich:spacer>
									<div class="row">
										<h:outputText
											rendered="#{brand_label_tracking_action.feeflg ne 'T'}"
											value="Total Label Fee : #{brand_label_tracking_action.labelchallanfee}"
											style="COLOR: #000080; font-family: serif; font-size: 18px"></h:outputText>


									</div>

									<div class="row">
										<rich:dataTable id="table3" rows="10" width="100%"
											value="#{brand_label_tracking_action.showUploadedLabels}"
											var="list" headerClass="TableHead" footerClass="TableHead"
											rowClasses="TableRow1,TableRow2">



											<rich:column>
												<f:facet name="header">
													<h:outputText value="Sr. No."
														style="FONT-FAMILY: 'Times New Roman'; FONT-WEIGHT: bold; FONT-SIZE: small;">
													</h:outputText>
												</f:facet>
												<h:outputText value="#{list.showUploadedSrNo}"
													styleClass="generalHeaderStyleOutput">
												</h:outputText>
											</rich:column>

											<rich:column>
												<f:facet name="header">
													<h:outputText value="Size(ml)"
														style="FONT-FAMILY: 'Times New Roman'; FONT-WEIGHT: bold; FONT-SIZE: small;">
													</h:outputText>
												</f:facet>
												<h:outputText value="#{list.showsize}"
													styleClass="generalHeaderStyleOutput">
												</h:outputText>
											</rich:column>



											<rich:column>
												<f:facet name="header">
													<h:outputText value="Label Description"
														style="FONT-FAMILY: 'Times New Roman'; FONT-WEIGHT: bold; FONT-SIZE: small;">
													</h:outputText>
												</f:facet>
												<h:outputText value="#{list.showUploadedDescription}"
													styleClass="generalHeaderStyleOutput">
												</h:outputText>
											</rich:column>


											<rich:column>
												<f:facet name="header">
													<h:outputText value="Labels"
														style="FONT-FAMILY: 'Times New Roman'; FONT-WEIGHT: bold; FONT-SIZE: small;">
													</h:outputText>
												</f:facet>

												<h:outputLink target="_blank"
													value="#{list.showUploadedImage}">

													<h:graphicImage value="/img/download.gif"
														alt="view document" style="width : 60px; height : 35px;"></h:graphicImage>
												</h:outputLink>


											</rich:column>

											<rich:column>
												<f:facet name="header">
													<h:outputText value="Affidavit"
														style="FONT-FAMILY: 'Times New Roman'; FONT-WEIGHT: bold; FONT-SIZE: small;">
													</h:outputText>
												</f:facet>

												<h:outputLink target="_blank"
													value="#{list.showUploadedAffidavit}">

													<h:graphicImage value="/img/download.gif"
														alt="view document" style="width : 60px; height : 35px;"></h:graphicImage>
												</h:outputLink>


											</rich:column>
											<rich:column
												rendered="#{brand_label_tracking_action.renew_new eq  'Renew'}">
												<f:facet name="header">

													<h:outputText value="2019-20 Brand Approval Letter"
														style="FONT-FAMILY: 'Times New Roman'; FONT-WEIGHT: bold; FONT-SIZE: small;">
													</h:outputText>
												</f:facet>

												<h:outputLink target="_blank" value="#{list.showUploadedBR}">

													<h:graphicImage value="/img/download.gif"
														alt="view document" style="width : 60px; height : 35px;"></h:graphicImage>
												</h:outputLink>


											</rich:column>

											<rich:column
												rendered="#{brand_label_tracking_action.renew_new ne  'Renew'}">
												<f:facet name="header">
													<h:outputText value="Trademark Registration"
														style="FONT-FAMILY: 'Times New Roman'; FONT-WEIGHT: bold; FONT-SIZE: small;">
													</h:outputText>

												</f:facet>

												<h:outputLink target="_blank" value="#{list.showUploadedBR}">

													<h:graphicImage value="/img/download.gif"
														alt="view document" style="width : 60px; height : 35px;"></h:graphicImage>
												</h:outputLink>


											</rich:column>
											<rich:column
												rendered="#{brand_label_tracking_action.renew_new ne  'Renew' and brand_label_tracking_action.licenseType eq 'FL2D'}">
												<f:facet name="header">
													<h:outputText value="Authorization of Brand ownership"
														style="FONT-FAMILY: 'Times New Roman'; FONT-WEIGHT: bold; FONT-SIZE: small;">
													</h:outputText>

												</f:facet>

												<h:outputLink target="_blank"
													value="#{list.showUploadedManualRcpt}">

													<h:graphicImage value="/img/download.gif"
														alt="view document" style="width : 60px; height : 35px;"></h:graphicImage>
												</h:outputLink>


											</rich:column>
											<rich:column
												rendered="#{brand_label_tracking_action.renew_new ne  'Renew' and brand_label_tracking_action.licenseType ne 'FL2D'}">
												<f:facet name="header">
													<h:outputText value="Certificate of Brand ownership"
														style="FONT-FAMILY: 'Times New Roman'; FONT-WEIGHT: bold; FONT-SIZE: small;">
													</h:outputText>

												</f:facet>

												<h:outputLink target="_blank"
													value="#{list.showUploadedManualRcpt}">

													<h:graphicImage value="/img/download.gif"
														alt="view document" style="width : 60px; height : 35px;"></h:graphicImage>
												</h:outputLink>


											</rich:column>
											<rich:column
												rendered="#{brand_label_tracking_action.renew_new eq  'Renew'}">
												<f:facet name="header">

													<h:outputText value="2019-20 Label Approval"
														style="FONT-FAMILY: 'Times New Roman'; FONT-WEIGHT: bold; FONT-SIZE: small;">
													</h:outputText>
												</f:facet>

												<h:outputLink target="_blank"
													value="#{list.showUploadedManualRcpt}">

													<h:graphicImage value="/img/download.gif"
														alt="view document" style="width : 60px; height : 35px;"></h:graphicImage>
												</h:outputLink>


											</rich:column>

											<f:facet name="footer">
												<rich:datascroller for="table3"></rich:datascroller>
											</f:facet>
										</rich:dataTable>
									</div>
								</div>
								<div class="row">
									<rich:spacer height="10px"></rich:spacer>
								</div>
							</div>
						</div>
						<div align="center">
					<h:commandButton styleClass="btn btn-sm btn-info"
					 action="#{brand_label_tracking_action.close}" value="Close"></h:commandButton>
					</div>
					</h:panelGroup>

				</div>
				<div class="row" align="center">
					<rich:spacer height="10px"></rich:spacer>
				</div>
                     </div>
		</h:form>
   </f:view>
</ui:composition>