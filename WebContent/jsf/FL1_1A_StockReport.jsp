<ui:composition xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:a4j="http://richfaces.org/a4j"
	xmlns:rich="http://richfaces.org/rich">
	<f:view>
		<h:form style="background-color:whitesmoke;">

			<div class="panel panel-default">
				<div class="panel-body">
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

					<TABLE width="100%" align="center">
						<TR align="center" style="FONT-SIZE: x-large; FONT-WEIGHT: bold;">
							<TD><h:inputHidden
									value="#{fL1_1A_StockReportAction.hidden}"></h:inputHidden>
								<h5>
									<h:outputText
										value="Stock Report Of FL1 / FL1A"
										style="FONT-STYLE: italic; COLOR: #0000a0; FONT-WEIGHT: bold; FONT-SIZE: x-large;"></h:outputText>

								</h5></TD>
						</TR>
						<TR align="center" style="FONT-SIZE: x-large; FONT-WEIGHT: bold;">
							<TD><rich:separator lineType="dashed"></rich:separator></TD>
						</TR>
						<tr>
							<TD><rich:spacer height="10px"></rich:spacer></TD>
						</tr>
						<tr>
							<TD align="center" colspan="2"><h:outputLabel
									value="#{fL1_1A_StockReportAction.name}"
									style="COLOR: #000000; FONT-SIZE: x-large;"></h:outputLabel></TD>
						</tr>
						<tr>
							<TD><rich:spacer height="5px"></rich:spacer></TD>
						</tr>
						<TR>

							<TD align="center" colspan="2"><h:outputLabel
									value="#{fL1_1A_StockReportAction.address}"
									style="COLOR: #000000; FONT-SIZE: medium;"></h:outputLabel></TD>

						</TR>
						<tr>
							<TD><rich:spacer height="10px"></rich:spacer></TD>
						</tr>
						<TR align="center" style="FONT-SIZE: x-large; FONT-WEIGHT: bold;">
							<TD><rich:separator lineType="dashed"></rich:separator></TD>
						</TR>
						<tr>
							<TD><rich:spacer height="30px"></rich:spacer></TD>
						</tr>
					</TABLE>


					<div class="row col-md-12" align="center"
						style="BACKGROUND-COLOR: #dee0e2;">
						<div class="col-md-12" align="center">
							<h:selectOneRadio style="FONT-WEIGHT: bold;  font-size: 15px; "
								value="#{fL1_1A_StockReportAction.radio}"
								valueChangeListener="#{fL1_1A_StockReportAction.radioListener}"
								onchange="this.form.submit();">
								<f:selectItem itemValue="FL1" itemLabel="FL1" />
								<f:selectItem itemValue="FL1A" itemLabel="FL1A" />

							</h:selectOneRadio>
						</div>
					</div>

					<div class="row">
						<rich:spacer height="20px"></rich:spacer>
					</div>

					<div class="col-md=12" align="center">
						<h:outputText style="FONT-SIZE: 15px; FONT-WEIGHT: bold;"
							rendered="#{fL1_1A_StockReportAction.radio eq 'FL1A'}"
							value="License Number:"></h:outputText>
						<h:selectOneMenu 
							value="#{fL1_1A_StockReportAction.licenseNo}"
							style="width: 150px; height: 28px;">
							<f:selectItems
								value="#{fL1_1A_StockReportAction.fl1aLicenseList}" />
						</h:selectOneMenu>
					</div>

					<div class="row">
						<rich:spacer height="20px"></rich:spacer>
					</div>
					
					<div class="row" align="center">
						<h:commandButton value="Print Report" styleClass="btn btn-success"
							action="#{fL1_1A_StockReportAction.print}">
						</h:commandButton>

						<h:outputLink styleClass="outputLinkEx"
							value="/doc/ExciseUp/Distillery/pdf/#{fL1_1A_StockReportAction.pdfname}"
							target="_blank">
							<h:outputText styleClass="outputText" id="text223"
								value="View Report"
								rendered="#{fL1_1A_StockReportAction.printFlag==true}"
								style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
						</h:outputLink>

						<rich:spacer width="10px;"></rich:spacer>
						<h:commandButton value="Reset" styleClass="btn btn-warning"
							action="#{fL1_1A_StockReportAction.reset}">
						</h:commandButton>
					</div>


					<div class="row " align="center">
						<rich:spacer height="20px"></rich:spacer>
					</div>
					<br />
				</div>
			</div>
		</h:form>
	</f:view>
</ui:composition>