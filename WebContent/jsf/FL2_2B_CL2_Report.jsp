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
							<h:outputText value=" FL2 / FL2B / CL2 Report "
								styleClass="generalHeaderOutputTable"
								style="font-weight: bold; font-size: 40px;text-decoration: underline;margin-top: 2px;font-family:Monotype Corsiva;font-size:40px;" />
						</h2>
					</div>
				</div>

				<div class="row " align="center">
					<rich:spacer height="10px"></rich:spacer>
				</div>
				<rich:spacer height="30px"></rich:spacer>
				<div class="row " align="center">
					 <div align="center">
					<h:outputText value="Select Year: "
						style="FONT-SIZE:small; FONT-WEIGHT: bold;"></h:outputText>
                   <rich:spacer width="20px"></rich:spacer>
					<h:selectOneMenu
						value="#{fL2_2B_CL2_ReportAction.year}"
						style="height: 25px; width : 150px;">
						<f:selectItems
							value="#{fL2_2B_CL2_ReportAction.getAll_List}"/>
					</h:selectOneMenu>
				</div>
				</div>
                 <rich:spacer height="30px"></rich:spacer>

				<div class="row col-md-12" align="center"
					style="BACKGROUND-COLOR: #dee0e2;">
					<div class="col-md-12" align="center">
						<h:selectOneRadio
							style="FONT-WEIGHT: bold;  font-size: 18px; width: 20%; "
							value="#{fL2_2B_CL2_ReportAction.radio}" >
							<f:selectItem itemValue="FL2" itemLabel="FL2" />
							<f:selectItem itemValue="FL2B" itemLabel="FL2B" />
							<f:selectItem itemValue="CL2" itemLabel="CL2" />

						</h:selectOneRadio>
					</div>
				</div>

				<div class="row">
					<rich:spacer height="20px"></rich:spacer>
				</div>

				<div class="col-md=12" align="center">
					<div class="col-md-3"></div>
					<div class="col-md-1" align="right">From:</div>
					<div class="col-md-2" align="left">
						<rich:calendar value="#{fL2_2B_CL2_ReportAction.fromDate}">
						</rich:calendar>
					</div>
					<div class="col-md-1" align="right">To:</div>
					<div class="col-md-2" align="left">
						<rich:calendar value="#{fL2_2B_CL2_ReportAction.toDate}">
						</rich:calendar>
					</div>
					<div class="col-md-3"></div>
				</div>

				<div class="row " align="center">
					<rich:spacer height="30px"></rich:spacer>
				</div>
				<br /> <br />
				<div class="row" align="center">
					<h:commandButton value="Print Report" styleClass="btn btn-success"
						action="#{fL2_2B_CL2_ReportAction.print}">
					</h:commandButton>

					<h:outputLink styleClass="outputLinkEx"
						value="/doc/ExciseUp/MIS/pdf/#{fL2_2B_CL2_ReportAction.pdfName}"
						target="_blank">
						<h:outputText styleClass="outputText" id="text223"
							value="View Report"
							rendered="#{fL2_2B_CL2_ReportAction.printFlag==true}"
							style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
					</h:outputLink>

					<rich:spacer width="10px;"></rich:spacer>
					<h:commandButton value="Reset" styleClass="btn btn-warning"
						action="#{fL2_2B_CL2_ReportAction.reset}">
					</h:commandButton>
				</div>
				<br />
				<div class="col-md-12" align="center">
					<h:commandButton styleClass="btn btn-primary"
						action="#{fL2_2B_CL2_ReportAction.excel}" value="Generate Excel"
						rendered="true" />

					<h:outputLink styleClass="outputLinkEx"
						value="/doc/ExciseUp//MIS//Excel//#{fL2_2B_CL2_ReportAction.exlname}.xlsx"
						target="_blank">
						<h:outputText styleClass="outputText" value="Download Excel"
							rendered="#{fL2_2B_CL2_ReportAction.excelFlag==true}"
							style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
					</h:outputLink>
				</div>

				<div class="row " align="center">
					<rich:spacer height="20px"></rich:spacer>
				</div>
				<br />
			</div>
		</h:form>
	</f:view>
</ui:composition>