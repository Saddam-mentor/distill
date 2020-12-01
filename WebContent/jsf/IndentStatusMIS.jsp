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
													<h:outputText value="Wholesale Indent Status"
														style="COLOR: #0000a0; FONT-WEIGHT: bold; FONT-SIZE: 35px;font-family:Monotype Corsiva;">
													</h:outputText>
													<h:inputHidden value="#{indentStatusMISAction.hidden}" />
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

				<h:panelGroup>
					<div class="row" align="center">
						<div class="col-md-12">
							<h:selectOneRadio style="width:30%"
								value="#{indentStatusMISAction.cate_type}"
								valueChangeListener="#{indentStatusMISAction.cate_typeLisnr}"
								onchange="this.form.submit();">
								<f:selectItem itemValue="D" itemLabel="Distillery" />
								<f:selectItem itemValue="B" itemLabel="Brewery" />
								<f:selectItem itemValue="BWFL" itemLabel="BWFL" />
								<f:selectItem itemValue="FL2D" itemLabel="FL2D" />
							</h:selectOneRadio>
						</div>
						<div class="row">
							<rich:spacer height="15px"></rich:spacer>
						</div>
						<div class="col-md-12" align="center">
							<h:selectOneMenu value="#{indentStatusMISAction.dropdown}"
								onchange="this.form.submit();"
								valueChangeListener="#{indentStatusMISAction.dropdownLisnr}"
								style="width: 250px; height: 28px;">
								<f:selectItems value="#{indentStatusMISAction.dropdownList}" />
							</h:selectOneMenu>
						</div>
						<div class="row">
							<rich:spacer height="20px"></rich:spacer>
						</div>
						<div class="row" align="center" style="BACKGROUND-COLOR: #c3e1f4;">
							<div class="col-md-12" align="center">
								<h:selectOneRadio style="width:60%"
									value="#{indentStatusMISAction.radioType}"
									valueChangeListener="#{indentStatusMISAction.radioListener}"
									onchange="this.form.submit();">
									<f:selectItem itemValue="P" itemLabel="Pending" />
									<f:selectItem itemValue="A" itemLabel="Accepted" />
									<f:selectItem itemValue="O" itemLabel="Objected" />
									<f:selectItem itemValue="D" itemLabel="Delivered" />
									<f:selectItem itemValue="C" itemLabel="Cancelled" />
									<f:selectItem itemValue="RJ"
										itemLabel="Recommended For Cancellation" />

								</h:selectOneRadio>
							</div>
						</div>
					</div>
					<div class="row">
						<rich:spacer height="20px"></rich:spacer>
					</div>

					<div class="row" align="center">
						<div class="col-md-12">
							<rich:dataTable id="table22" rows="10" width="100%"
								value="#{indentStatusMISAction.indentDetailsList}" var="list"
								headerClass="TableHead" footerClass="TableHead"
								styleClass="DataTable" rowClasses="TableRow1,TableRow2">


								<rich:column>
									<f:facet name="header">
										<h:outputText value="Sr.No">
										</h:outputText>
									</f:facet>
									<h:outputText style="margin-left: 20px;" value="#{list.srNo}"></h:outputText>
								</rich:column>

								<rich:column>
									<f:facet name="header">
										<h:outputText value="Indent No." />
									</f:facet>
									<center>
										<h:outputText styleClass="generalExciseStyle"
											value="#{list.indentNmbr_dt}" />
									</center>
								</rich:column>

								<rich:column>
									<f:facet name="header">
										<h:outputText value="Indent Date">
										</h:outputText>
									</f:facet>
									<h:outputText styleClass="generalExciseStyle"
										value="#{list.showIndentDateTym_dt}">
									</h:outputText>
								</rich:column>

								<rich:column>
									<f:facet name="header">
										<h:outputText value="Wholesale ID" />
									</f:facet>
									<center>
										<h:outputText styleClass="generalExciseStyle"
											value="#{list.wholesaleLicensenmbr_dt}" />
									</center>
								</rich:column>
								<rich:column>
									<f:facet name="header">
										<h:outputText value="Licensee Name" />
									</f:facet>
									<center>
										<h:outputText styleClass="generalExciseStyle"
											value="#{list.licenseeName_dt}" />
									</center>
								</rich:column>
								<rich:column>
									<f:facet name="header">
										<h:outputText value="District" />
									</f:facet>
									<center>
										<h:outputText styleClass="generalExciseStyle"
											value="#{list.districtName_dt}" />
									</center>
								</rich:column>

								<rich:column>
									<f:facet name="header">
										<h:outputText value="No.Of Cases Indented" />
									</f:facet>
									<center>
										<h:outputText styleClass="generalExciseStyle"
											value="#{list.nmbrOfCases_dt}" />
									</center>
								</rich:column>


								<rich:column>
									<f:facet name="header">
										<h:outputText value="No.Of Cases Supplied" />
									</f:facet>
									<center>
										<h:outputText styleClass="generalExciseStyle"
											value="#{list.nmbrOfCasesSuplied_dt}" />
									</center>
								</rich:column>
								<rich:column style="text-align: center">
									<f:facet name="header">
										<h:outputText value="Status" />
									</f:facet>
									<h:outputText value="#{list.status_dt}" />
								</rich:column>




								<f:facet name="footer">
									<rich:datascroller for="table22"></rich:datascroller>
								</f:facet>
							</rich:dataTable>

						</div>
					</div>
				</h:panelGroup>

				<br /> <br />



				<div class="row" align="center">
					<rich:spacer height="10px"></rich:spacer>
				</div>



			</div>
		</h:form>



	</f:view>
</ui:composition>