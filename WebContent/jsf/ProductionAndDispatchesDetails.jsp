<ui:composition xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:a4j="http://richfaces.org/a4j"
	xmlns:rich="http://richfaces.org/rich">
	<f:view>
		<head>
<style type="text/css">
.dropdown {
	width: 40%;
	text-align: center;
	height: 35px;
	padding: 5px;
	color: black;
	border: 1px solid #E0E0E0;
	border-radius: 4px;
}

.textt {
	width: 40%;
	height: 32px;
	padding: 5px;
	color: black;
	border: 1px solid #E0E0E0;
	border-radius: 4px;
}
</style>
		</head>
		<h:form>
			<table align="center">
				<tr>
					<th><h3>
							<h:messages errorStyle="color:red" layout="TABLE" id="messages"
								infoStyle="color:green" />
						</h3></th>
				</tr>
				<tr height="20px"></tr>
				<tr align="center">
					<td><h:inputHidden
							value="#{productionAndDispatchesDetailsAction.hidden}"></h:inputHidden>
						<h5>
							<h:outputText value=" Production And Dispatch Details "
								style="FONT-STYLE: italic; COLOR: #0000a0; FONT-WEIGHT: bold; FONT-SIZE: x-large;"></h:outputText>
						</h5></td>
				</tr>
			</table>
			<div style="text-align: right; margin-left: 35px;">
				<table align="center">
					<tr>


						<TD colspan="4" align="center" style="FONT-WEIGHT: bold;">Date
							: <rich:calendar
								value="#{productionAndDispatchesDetailsAction.date_dt}"
								onchanged="this.form.submit();"
								valueChangeListener="#{productionAndDispatchesDetailsAction.tankChange}"></rich:calendar>
						</TD>
					</tr>
				</table>
			</div>
			<br />
			<br />
			<div class="panel panel-default">
				<div class="panel-body">
					<TABLE width="80%" align="center">
						<TR>
							<TD colspan="2" align="center">
								<div class="panel panel-default">
									<div class="panel-body">

										<table>
											<tbody>

												<tr align="center">
													<td>
														<h4>
															<h:outputText value="Production "
																style="color:#02486d; font-family: calibri; font-size:25px; TEXT-DECORATION: underline; text-align: center;" />
														</h4>
													</td>
												</tr>
											</tbody>
										</table>

										<TABLE align="center" width="100%" style="margin-left: 290px;">
											<TBODY>

												<tr>
													<td align="center" width="250px"></td>


													<TD align="center" width="350px"><h5>
															<h:outputText value="BL" style="FONT-WEIGHT: bold;"></h:outputText>
														</h5></TD>
													<td></td>
													<TD width="150px"><h5>
															<h:outputText value="Cases" style="FONT-WEIGHT: bold;"></h:outputText>
														</h5></TD>
													<td></td>
													<TD width="770px"><h5>
															<h:outputText value="Bottles" style="FONT-WEIGHT: bold;"></h:outputText>
														</h5></TD>


												</tr>
											</TBODY>
										</TABLE>
										<TABLE align="center" width="100%" style="margin-left: 285px;">

											<TBODY>

												<tr>
													<TD align="left" width="250px"><h5>
															<h:outputText value="Total Bottling(CL)"
																style="FONT-WEIGHT: bold;"></h:outputText>
														</h5></TD>

													<TD align="center" width="300px"><h5>
															<h:outputText
																value="#{productionAndDispatchesDetailsAction.bl_cl2}"
																styleClass="textt" style=" width : 90px;">
																<f:convertNumber maxFractionDigits="2"
																	pattern="#############0.00" />
															</h:outputText>
														</h5></TD>

													<TD width="150px"><h5>
															<h:outputText
																value="#{productionAndDispatchesDetailsAction.cases_cl2}"
																styleClass="textt" style=" width : 83px;" />
														</h5></TD>
													<td></td>
													<TD width="890px"><h5>
															<h:outputText
																value="#{productionAndDispatchesDetailsAction.bottles_cl2}"
																styleClass="textt" style=" width : 132px;" />
														</h5></TD>


												</tr>
												<tr>
													<TD align="left" width="250px"><h5>
															<h:outputText value="Total Bottling (FL)"
																style="FONT-WEIGHT: bold;"></h:outputText>
														</h5></TD>

													<TD align="center" width="300px"><h5>
															<h:outputText
																value="#{productionAndDispatchesDetailsAction.bl_fl2}"
																styleClass="textt" style=" width : 90px;">
																<f:convertNumber maxFractionDigits="2"
																	pattern="#############0.00" />
															</h:outputText>
														</h5></TD>

													<TD width="150px"><h5>
															<h:outputText
																value="#{productionAndDispatchesDetailsAction.cases_fl2}"
																styleClass="textt" style=" width : 83px;" />
														</h5></TD>
													<td></td>
													<TD width="890px"><h5>
															<h:outputText
																value="#{productionAndDispatchesDetailsAction.bottles_fl2}"
																styleClass="textt" style=" width : 132px;" />
														</h5></TD>


												</tr>
												<tr>
													<TD align="left" width="250px"><h5>
															<h:outputText value="Total Bottling (BEER)"
																style="FONT-WEIGHT: bold;"></h:outputText>
														</h5></TD>

													<TD align="center" width="300px"><h5>
															<h:outputText
																value="#{productionAndDispatchesDetailsAction.bl_beer2}"
																styleClass="textt" style=" width : 90px;">
																<f:convertNumber maxFractionDigits="2"
																	pattern="#############0.00" />
															</h:outputText>
														</h5></TD>

													<TD width="150px"><h5>
															<h:outputText
																value="#{productionAndDispatchesDetailsAction.cases_beer2}"
																styleClass="textt" style=" width : 83px;" />
														</h5></TD>
													<td></td>
													<TD width="890px"><h5>
															<h:outputText
																value="#{productionAndDispatchesDetailsAction.bottles_beer2}"
																styleClass="textt" style=" width : 132px;" />
														</h5></TD>


												</tr>


											</TBODY>
										</TABLE>
										`
										<rich:separator lineType="dashed"></rich:separator>

										<table>
											<tbody>

												<tr align="center">
													<td>
														<h4>
															<h:outputText
																value="Dispatches To Wholesale"
																style="color:#02486d; font-family: calibri; font-size:25px; TEXT-DECORATION: underline; text-align: center;" />
														</h4>
													</td>
												</tr>
											</tbody>
										</table>

										<TABLE align="center" width="100%" style="margin-left: 290px;">
											<TBODY>

												<tr>
													<td align="center" width="250px"></td>


													<TD align="center" width="350px"><h5>
															<h:outputText value="BL" style="FONT-WEIGHT: bold;"></h:outputText>
														</h5></TD>
													<td></td>
													<TD width="150px"><h5>
															<h:outputText value="Cases" style="FONT-WEIGHT: bold;"></h:outputText>
														</h5></TD>
													<td></td>
													<TD width="770px"><h5>
															<h:outputText value="Bottles" style="FONT-WEIGHT: bold;"></h:outputText>
														</h5></TD>


												</tr>
											</TBODY>
										</TABLE>
										<TABLE align="center" width="100%" style="margin-left: 285px;">
											<TBODY>

												<tr>
													<TD align="left" width="250px"><h5>
															<h:outputText value="Total Dispatches(CL/PD-25)"
																style="FONT-WEIGHT: bold;"></h:outputText>
														</h5></TD>

													<TD align="center" width="300px"><h5>
															<h:outputText
																value="#{productionAndDispatchesDetailsAction.bl_cl}"
																styleClass="textt" style=" width : 90px;">
																<f:convertNumber maxFractionDigits="2"
																	pattern="#############0.00" />
															</h:outputText>

														</h5></TD>

													<TD width="150px"><h5>
															<h:outputText
																value="#{productionAndDispatchesDetailsAction.cases_cl}"
																styleClass="textt" style=" width : 83px;" />
														</h5></TD>
													<td></td>
													<TD width="890px"><h5>
															<h:outputText
																value="#{productionAndDispatchesDetailsAction.bottles_cl}"
																styleClass="textt" style=" width : 132px;" />
														</h5></TD>


												</tr>
												<tr>
													<TD align="left" width="250px"><h5>
															<h:outputText value="Total Dispatches (FL)"
																style="FONT-WEIGHT: bold;"></h:outputText>
														</h5></TD>

													<TD align="center" width="300px"><h5>
															<h:outputText
																value="#{productionAndDispatchesDetailsAction.bl_fl}"
																styleClass="textt" style=" width : 90px;">
																<f:convertNumber maxFractionDigits="2"
																	pattern="#############0.00" />
															</h:outputText>
														</h5></TD>

													<TD width="150px"><h5>
															<h:outputText
																value="#{productionAndDispatchesDetailsAction.cases_fl}"
																styleClass="textt" style=" width : 83px;" />
														</h5></TD>
													<td></td>
													<TD width="890px"><h5>
															<h:outputText
																value="#{productionAndDispatchesDetailsAction.bottles_fl}"
																styleClass="textt" style=" width : 132px;" />
														</h5></TD>


												</tr>
												<tr>
													<TD align="left" width="250px"><h5>
															<h:outputText value="Total Dispatches (BEER)"
																style="FONT-WEIGHT: bold;"></h:outputText>
														</h5></TD>

													<TD align="center" width="300px"><h5>
															<h:outputText
																value="#{productionAndDispatchesDetailsAction.bl_beer}"
																styleClass="textt" style=" width : 90px;">
																<f:convertNumber maxFractionDigits="2"
																	pattern="#############0.00" />
															</h:outputText>
														</h5></TD>

													<TD width="150px"><h5>
															<h:outputText
																value="#{productionAndDispatchesDetailsAction.cases_beer}"
																styleClass="textt" style=" width : 83px;" />
														</h5></TD>
													<td></td>
													<TD width="890px"><h5>
															<h:outputText
																value="#{productionAndDispatchesDetailsAction.bottles_beer}"
																styleClass="textt" style=" width : 132px;" />
														</h5></TD>


												</tr>

												<tr>
													<TD align="left" width="250px"><h5>
															<h:outputText value="Total Dispatches (BWFL2A)"
																style="FONT-WEIGHT: bold;"></h:outputText>
														</h5></TD>

													<TD align="center" width="300px"><h5>
															<h:outputText
																value="#{productionAndDispatchesDetailsAction.bl_bwfl2a}"
																styleClass="textt" style=" width : 90px;">
																<f:convertNumber maxFractionDigits="2"
																	pattern="#############0.00" />
															</h:outputText>
														</h5></TD>

													<TD width="150px"><h5>
															<h:outputText
																value="#{productionAndDispatchesDetailsAction.cases_bwfl2a}"
																styleClass="textt" style=" width : 83px;" />
														</h5></TD>
													<td></td>
													<TD width="890px"><h5>
															<h:outputText
																value="#{productionAndDispatchesDetailsAction.bottles_bwfl2a}"
																styleClass="textt" style=" width : 132px;" />
														</h5></TD>


												</tr>
												
												<tr>
													<TD align="left" width="250px"><h5>
															<h:outputText value="Total Dispatches (BWFL2B)"
																style="FONT-WEIGHT: bold;"></h:outputText>
														</h5></TD>

													<TD align="center" width="300px"><h5>
															<h:outputText
																value="#{productionAndDispatchesDetailsAction.bl_bwfl2b}"
																styleClass="textt" style=" width : 90px;">
																<f:convertNumber maxFractionDigits="2"
																	pattern="#############0.00" />
															</h:outputText>
														</h5></TD>

													<TD width="150px"><h5>
															<h:outputText
																value="#{productionAndDispatchesDetailsAction.cases_bwfl2b}"
																styleClass="textt" style=" width : 83px;" />
														</h5></TD>
													<td></td>
													<TD width="890px"><h5>
															<h:outputText
																value="#{productionAndDispatchesDetailsAction.bottles_bwfl2b}"
																styleClass="textt" style=" width : 132px;" />
														</h5></TD>


												</tr>
												
												<tr>
													<TD align="left" width="250px"><h5>
															<h:outputText value="Total Dispatches (BWFL2C)"
																style="FONT-WEIGHT: bold;"></h:outputText>
														</h5></TD>

													<TD align="center" width="300px"><h5>
															<h:outputText
																value="#{productionAndDispatchesDetailsAction.bl_bwfl2c}"
																styleClass="textt" style=" width : 90px;">
																<f:convertNumber maxFractionDigits="2"
																	pattern="#############0.00" />
															</h:outputText>
														</h5></TD>

													<TD width="150px"><h5>
															<h:outputText
																value="#{productionAndDispatchesDetailsAction.cases_bwfl2c}"
																styleClass="textt" style=" width : 83px;" />
														</h5></TD>
													<td></td>
													<TD width="890px"><h5>
															<h:outputText
																value="#{productionAndDispatchesDetailsAction.bottles_bwfl2c}"
																styleClass="textt" style=" width : 132px;" />
														</h5></TD>


												</tr>
												
												<tr>
													<TD align="left" width="250px"><h5>
															<h:outputText value="Total Dispatches (BWFL2D)"
																style="FONT-WEIGHT: bold;"></h:outputText>
														</h5></TD>

													<TD align="center" width="300px"><h5>
															<h:outputText
																value="#{productionAndDispatchesDetailsAction.bl_bwfl2d}"
																styleClass="textt" style=" width : 90px;">
																<f:convertNumber maxFractionDigits="2"
																	pattern="#############0.00" />
															</h:outputText>
														</h5></TD>

													<TD width="150px"><h5>
															<h:outputText
																value="#{productionAndDispatchesDetailsAction.cases_bwfl2d}"
																styleClass="textt" style=" width : 83px;" />
														</h5></TD>
													<td></td>
													<TD width="890px"><h5>
															<h:outputText
																value="#{productionAndDispatchesDetailsAction.bottles_bwfl2d}"
																styleClass="textt" style=" width : 132px;" />
														</h5></TD>


												</tr>

												<tr>
													<TD align="left" width="250px"><h5>
															<h:outputText value="Total Dispatches (FL2D)"
																style="FONT-WEIGHT: bold;"></h:outputText>
														</h5></TD>

													<TD align="center" width="300px"><h5>
															<h:outputText
																value="#{productionAndDispatchesDetailsAction.bl_fl2d}"
																styleClass="textt" style=" width : 90px;">
																<f:convertNumber maxFractionDigits="2"
																	pattern="#############0.00" />
															</h:outputText>
														</h5></TD>

													<TD width="150px"><h5>
															<h:outputText
																value="#{productionAndDispatchesDetailsAction.cases_fl2d}"
																styleClass="textt" style=" width : 83px;" />
														</h5></TD>
													<td></td>
													<TD width="890px"><h5>
															<h:outputText
																value="#{productionAndDispatchesDetailsAction.bottles_fl2d}"
																styleClass="textt" style=" width : 132px;" />
														</h5></TD>


												</tr>

											</TBODY>
										</TABLE>

										<table>
											<tbody>

												<tr align="center">
													<td>
														<h4>
															<h:outputText value=" Duty "
																style="color:#02486d; font-family: calibri; font-size:25px; TEXT-DECORATION: underline; text-align: center;" />
														</h4>
													</td>
												</tr>
											</tbody>
										</table>

										<TABLE align="center" width="100%" style="margin-left: 285px;">

											<TBODY>

												<tr>
													<TD align="left" style="width: 65px;"><h5>
															<h:outputText value=" Due :" style="FONT-WEIGHT: bold;"></h:outputText>
														</h5></TD>

													<TD align="left" width="300px" style="width: 575px;"><h5>
															<h:outputText
																value="#{productionAndDispatchesDetailsAction.due}"
																styleClass="textt" style=" width : 90px;" />
														</h5></TD>





												</tr>
												<tr>
													<TD align="left" width="30px"><h5>
															<h:outputText value="Deposit:" style="FONT-WEIGHT: bold;"></h:outputText>
														</h5></TD>

													<TD align="left" width="300px"><h5>
															<h:outputText
																value="#{productionAndDispatchesDetailsAction.deposit}"
																styleClass="textt" style=" width : 90px;" />
														</h5></TD>
												</tr>
											</TBODY>
										</TABLE>

									</div>
								</div>
							</TD>
						</TR>

					</TABLE>
				</div>
			</div>
		</h:form>
	</f:view>
</ui:composition>