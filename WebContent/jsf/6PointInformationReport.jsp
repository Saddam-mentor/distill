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


				<hr style="border-top: 7px #D0D3D4; border-top-style: dashed;"></hr>
				<div class="row">
					<div align="center" class="pghead">
						<h:outputText value="6 Point Information Report"
							style="COLOR: #0000a0; FONT-WEIGHT: bold; FONT-SIZE: 35px;font-family:Comic Sans MS;">
						</h:outputText>
					</div>
				</div>
				<hr style="border-top: 7px #D0D3D4; border-top-style: dashed;"></hr>

				<div class="row " align="center">
					<rich:spacer height="40px"></rich:spacer>
				</div>
				<br />
				<div class="row" align="center">
					<h:commandButton value="Print Report" styleClass="btn btn-success"
						action="#{pointInformationReportAction.print}">
					</h:commandButton>

					<h:outputLink styleClass="outputLinkEx"
						value="/doc/ExciseUp/MIS/pdf/#{pointInformationReportAction.pdfName}"
						target="_blank">
						<h:outputText styleClass="outputText" id="text223"
							value="View Report"
							rendered="#{pointInformationReportAction.printFlag}"
							style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
					</h:outputLink>

					<rich:spacer width="10px;"></rich:spacer>
					<h:commandButton action="#{pointInformationReportAction.excel}"
						styleClass="btn btn-primary" value="Generate Excel"
						rendered="true" />

					<h:outputLink styleClass="outputLinkEx"
						value="/doc/ExciseUp//MIS//Excel//#{pointInformationReportAction.excelName}.xlsx"
						target="_blank">
						<h:outputText styleClass="outputText" value="Download Excel"
							rendered="#{pointInformationReportAction.excelFlag==true}"
							style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
					</h:outputLink>

				</div>

				<br />

				<div class="col-md-12" align="center">
					<h:commandButton value="Reset" styleClass="btn btn-warning"
						action="#{pointInformationReportAction.reset}">
					</h:commandButton>
				</div>
				<div class="row " align="center">
					<rich:spacer height="20px"></rich:spacer>
				</div>
				<br />
			</div>
		</h:form>
	</f:view>
</ui:composition>