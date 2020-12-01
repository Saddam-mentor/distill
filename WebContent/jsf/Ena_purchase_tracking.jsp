
<ui:composition xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:a4j="http://richfaces.org/a4j"
	xmlns:rich="http://richfaces.org/rich"
	xmlns:p="http://primefaces.org/ui">

	<f:view>
		<h:form>


			<h:inputHidden value="#{ena_purchase_tracking_Action.hidden}" />
			<rich:spacer height="20"></rich:spacer>
			<div>
				<a4j:outputPanel id="msg">
					<h:messages errorStyle="color:red"
						style="font-size: 14px;font-weight: bold"
						styleClass="generalExciseStyle" layout="table" id="messages"
						infoStyle="color:green">
					</h:messages>
				</a4j:outputPanel>
			</div>

			<center>
				<h:outputText value="ENA Purchase Permit Tracking"
					style="FONT-STYLE: italic; COLOR: #0000a0; FONT-WEIGHT: bold; FONT-SIZE: x-large;">
				</h:outputText>
			</center>
			<rich:spacer height="20"></rich:spacer>
			<rich:separator lineType="dashed"></rich:separator>
			<rich:spacer height="20"></rich:spacer>
			<rich:spacer height="20"></rich:spacer>
			<h:panelGroup rendered="#{ena_purchase_tracking_Action.tableflag}">
			
			<rich:spacer height="20px"></rich:spacer>
			
			<div align="center">

				<h:selectOneRadio onchange="this.form.submit();"
					value="#{ena_purchase_tracking_Action.radio}"
					valueChangeListener="#{ena_purchase_tracking_Action.clickRadio}">
                    <f:selectItem itemValue="WUP" itemLabel="Within U.P"/>
					<f:selectItem itemValue="IM" itemLabel="Import"/>
					<f:selectItem itemValue="EX" itemLabel="Export"/>
				</h:selectOneRadio>
			</div>
			<rich:spacer height="20px"></rich:spacer>
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
																value="#{ena_purchase_tracking_Action.permit_no}"
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
																action="#{ena_purchase_tracking_Action.viewDetails}">
															</h:commandButton>
															<rich:spacer width="10px"></rich:spacer>
																<h:commandButton styleClass="btn btn-sm btn-danger"
																	action="#{ena_purchase_tracking_Action.reset}" value="Reset"></h:commandButton></td>
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


			<rich:spacer height="20"></rich:spacer>
           
           <h:panelGroup rendered="#{ena_purchase_tracking_Action.panelflag}">
			
			<rich:spacer height="20"></rich:spacer>
			<div class="row" align="center">
			
			<div class="col-md-12" align="center">


					<div class="col-md-2" align="right">
						<h:outputText value="Permit Number :"
							style="FONT-SIZE: 14px; FONT-WEIGHT: bold;"></h:outputText>
					</div>

					<div class="col-md-2" align="left" >
						<h:inputTextarea value="#{ena_purchase_tracking_Action.permit_no}" readonly="true"
							style="COLOR: #0000ff;">
						</h:inputTextarea>
					</div>

					<div class="col-md-2" align="right">
						<h:outputText value="Permit Date :"
							style="FONT-SIZE: 14px; FONT-WEIGHT: bold;"></h:outputText>
					</div>

					<div class="col-md-2" align="left">
						<h:inputText value="#{ena_purchase_tracking_Action.permit_date}"
							readonly="true" style="COLOR: #0000ff;">
						</h:inputText>
					</div>
					<div class="col-md-2" align="right">
						<h:outputText value=" Permit :"
							style="FONT-SIZE: 14px; FONT-WEIGHT: bold;"></h:outputText>
					</div>
					
					<div class="col-md-2" align="left">
						<h:outputLink
							value="/doc/ExciseUp/Distillery/ENA_order/#{ena_purchase_tracking_Action.permit}"
							target="_blank">
							<h:outputText value="view Permit"/>
						</h:outputLink>
					</div>
					
					
					

	</div>



				</div>
			<div>
			
			<rich:spacer height="10px"></rich:spacer>
			
			</div>
			
			<div class="row" align="center">
			<div class="col-md-12" align="center">


					<div class="col-md-2" align="right">
						<h:outputText value="Request Id :"
							style="FONT-SIZE: 14px; FONT-WEIGHT: bold;"></h:outputText>
					</div>

					<div class="col-md-2" align="left">
						<h:inputText value="#{ena_purchase_tracking_Action.req_id }" readonly="true"
							style="COLOR: #0000ff;">
						</h:inputText>
					</div>

					<div class="col-md-2" align="right">
						<h:outputText value="Request Date :"
							style="FONT-SIZE: 14px; FONT-WEIGHT: bold;"></h:outputText>
					</div>

					<div class="col-md-2" align="left">
						<h:inputText value="#{ena_purchase_tracking_Action.req_date }"
							readonly="true" style="COLOR: #0000ff;">
						</h:inputText>
					</div>

					<div class="col-md-2" align="right">
						<h:outputText value="Requested Quantity (BL)"
							style="FONT-SIZE: 14px; FONT-WEIGHT: bold;"></h:outputText>
					</div>

					<div class="col-md-2" align="left">
						<h:inputText value="#{ena_purchase_tracking_Action.requested_qty}"
							readonly="true" style="COLOR: #0000ff;">
							<f:convertNumber pattern="#0.00" />
						</h:inputText>
					</div>

</div>



				</div>
				 
			
				<div class="col-md-12">
					<div style="height: 50px"></div>
				</div>


				
    <div   class=" row newsdiv" style="background-color: #f5f5dc;" align="center">
					<div class="col-md-12" align="center"  >
						<h:outputLabel value="EXPORTER DETAIL "
							style="TEXT-DECORATION: underline; FONT-SIZE:  large;"></h:outputLabel>
					</div>

					<div class="col-md-12">
						<div style="height: 20px"></div>
					</div>
					<div class="col-md-12" align="center">
						<div align="right" class="col-md-2">
							<h:outputText value="Name of Exporting Distillery :"
								style="FONT-SIZE: 14px; FONT-WEIGHT: bold;"></h:outputText>
						</div>
						<div class="col-md-2" align="left">
							<h:inputTextarea value="#{ena_purchase_tracking_Action.from_distillery }"
								readonly="true" style="COLOR: #0000ff;">
							</h:inputTextarea>
						</div>
						<div align="right" class="col-md-2">
							<h:outputText value=" ENA Type : " style="FONT-WEIGHT: bold;" />
						</div>
						<div align="left" class="col-md-2">
							<h:inputText value="#{ena_purchase_tracking_Action.rad1}" readonly="true"
								style="COLOR: #0000ff;" />
						</div>
						<div align="right" class="col-md-2">

							<h:outputText value=" Purpose : " style="FONT-WEIGHT: bold;" />
						</div>
						<div align="left" class="col-md-2">
							<h:inputText value="#{ena_purchase_tracking_Action.rad2}" readonly="true"
								style="COLOR: #0000ff;" />

						</div>
					</div>

					

					<div class="col-md-12">
						<div style="height: 20px"></div>
					</div>

					<div align="center" class="col-md-12"
						style="background-color: #e2f1ee;">

						<div align="right" class="col-md-4">
							<h:outputText value=" Installed Capacity of Distillery(in BL) : "
								style="FONT-WEIGHT: bold;" />
						</div>
						<div align="left" class="col-md-3">
							<h:outputText value=" Potable : " style="FONT-WEIGHT: bold;" />
							<h:inputText value="#{ena_purchase_tracking_Action.instaldpotable_sel }"
								style="FONT-WEIGHT: bold;" readonly="true">
								<f:convertNumber pattern="#0.00" />
							</h:inputText>
						</div>
						<div align="left" class="col-md-3">
							<h:outputText value=" Industrial : " style="FONT-WEIGHT: bold;" />
							<h:inputText value="#{ena_purchase_tracking_Action.industrialpotable_sel }"
								style="FONT-WEIGHT: bold;" readonly="true">
								<f:convertNumber pattern="#0.00" />
							</h:inputText>
						</div>
						<div align="right" class="col-md-2"></div>


					</div>
					<div align="center" class="col-md-12"
						style="background-color: #e2f1ee;">
						<div align="right" class="col-md-4">
							<h:outputText value=" Production Quantity(in BL) : "
								style="FONT-WEIGHT: bold;" />
						</div>
						<div align="left" class="col-md-3">
							<h:outputText value=" Potable : " style="FONT-WEIGHT: bold;" />
							<h:inputText value="#{ena_purchase_tracking_Action.prodpotable_sel }"
								style="FONT-WEIGHT: bold;" readonly="true">
								<f:convertNumber pattern="#0.00" />
							</h:inputText>
						</div>
						<div align="left" class="col-md-3">
							<h:outputText value=" Industrial : " style="FONT-WEIGHT: bold;" />
							<h:inputText value="#{ena_purchase_tracking_Action.prodindstrial_sel }"
								style="FONT-WEIGHT: bold;" readonly="true">
								<f:convertNumber pattern="#0.00" />
							</h:inputText>
						</div>
						<div align="right" class="col-md-2"></div>
					</div>
					<div align="center" class="col-md-12"
						style="background-color: #e2f1ee;">
						<div align="right" class="col-md-4">
							<h:outputText value=" Purchase / Import Quantity(in BL) : "
								style="FONT-WEIGHT: bold;" />
						</div>
						<div align="left" class="col-md-3">
							<h:outputText value=" Potable : " style="FONT-WEIGHT: bold;" />
							<h:inputText value="#{ena_purchase_tracking_Action.purchsepotable_sel }"
								style="FONT-WEIGHT: bold;" readonly="true">
								<f:convertNumber pattern="#0.00" />
							</h:inputText>
						</div>
						<div align="left" class="col-md-3">
							<h:outputText value=" Industrial : " style="FONT-WEIGHT: bold;" />
							<h:inputText value="#{ena_purchase_tracking_Action.purchsindustrial_sel }"
								style="FONT-WEIGHT: bold;" readonly="true">
								<f:convertNumber pattern="#0.00" />
							</h:inputText>
						</div>
						<div align="right" class="col-md-2"></div>
					</div>

					<div align="center" class="col-md-12"
						style="background-color: #90da98;">
						<div align="right" class="col-md-4">
							<h:outputText value=" Balance : " style="FONT-WEIGHT: bold;" />
						</div>
						<div align="left" class="col-md-3">
							<h:outputText value=" Potable : " style="FONT-WEIGHT: bold;" />
							<h:inputText readonly="true"
								value="#{ena_purchase_tracking_Action.instaldpotable_sel-ena_purchase_tracking_Action.purchsepotable_sel-ena_purchase_tracking_Action.prodpotable_sel }"
								style="FONT-WEIGHT: bold;">
								<f:convertNumber pattern="#0.00" />
							</h:inputText>
						</div>
						<div align="left" class="col-md-3">
							<h:outputText value=" Industrial : " style="FONT-WEIGHT: bold;" />
							<h:inputText
								value="#{ena_purchase_tracking_Action.industrialpotable_sel-ena_purchase_tracking_Action.purchsindustrial_sel-ena_purchase_tracking_Action.prodindstrial_sel }"
								style="FONT-WEIGHT: bold;" readonly="true">
								<f:convertNumber pattern="#0.00" />
							</h:inputText>
						</div>
						<div align="right" class="col-md-2"></div>
					</div></div>
					
					
					
					
					
					
					
					<div class="col-md-12">
					<div style="height: 40px"></div>
				</div>
					
					<div   class=" row newsdiv" style="background-color: #f5f5dc;" align="center">
<div class="col-md-12" align="center" >
					<h:outputLabel value="IMPORTER DETAIL "
						style="TEXT-DECORATION: underline; FONT-SIZE:  large;"></h:outputLabel>
				</div>
				<div class="col-md-12">
					<div style="height: 10px"></div>
				</div>
				
				<div class="col-md-12 row" align="center">



					<div class="col-md-2" align="right">
						<h:outputText value="Name of Importing Distillery : "
							style="FONT-SIZE: 14px; FONT-WEIGHT: bold;"></h:outputText>
					</div>

					<div class="col-md-10" align="left">
						<h:outputText value="#{ena_purchase_tracking_Action.by_distillery}"
							readonly="true" style="COLOR: #0000ff;">
						</h:outputText>
					</div>


				</div>
<div class="col-md-12" align="center">
						<div class="col-md-2" align="right">
							<h:outputText value="Sale Agreement No.:"
								style="FONT-SIZE: 14px; FONT-WEIGHT: bold;"></h:outputText>
						</div>
						<div class="col-md-2" align="left">

							<h:outputLink
								value="/doc/ExciseUp/Distillery/ENA_pdf/#{ena_purchase_tracking_Action.pdfName}.pdf"
								target="_blank">
                                <h:outputText value="#{ena_purchase_tracking_Action.noc_no}"
									style="TEXT-DECORATION: underline; FONT-STYLE: italic; COLOR: #0000ff;"></h:outputText>
   </h:outputLink>

						</div>

						<div class="col-md-2" align="right">
							<h:outputText value="Sale Agreement Date.:"
								style="FONT-SIZE: 14px; FONT-WEIGHT: bold;"></h:outputText>
						</div>
						<div class="col-md-2" align="left">
							<h:inputText value="#{ena_purchase_tracking_Action.noc_date }"
								readonly="true">
							</h:inputText>
						</div>

						<div class="col-md-2" align="right">
							<h:outputText value="Sale From.:"
								style="FONT-SIZE: 14px; FONT-WEIGHT: bold;"></h:outputText>
						</div>
						<div class="col-md-2" align="left">
							<h:inputText value="#{ena_purchase_tracking_Action.salefrom }"
								readonly="true">
							</h:inputText>
						</div>

					</div>
					<div class="col-md-12">
						<div style="height: 10px"></div>
					</div>
</div>
				
				
				<div align="center">
					<h:commandButton styleClass="btn btn-sm btn-info"
					 action="#{ena_purchase_tracking_Action.close}" value="Close"></h:commandButton>
					</div>
</h:panelGroup>

		</h:form>

	</f:view>
	
	<h:form>
			 
</h:form>
	
  
</ui:composition>