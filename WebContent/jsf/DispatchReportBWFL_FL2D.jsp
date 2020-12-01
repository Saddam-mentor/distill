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
							<h:outputText value="Dispatches From BWFL / FL2D "
								styleClass="generalHeaderOutputTable"
								style="font-weight: bold; font-size: 40px;text-decoration: underline;margin-top: 2px;font-family:Monotype Corsiva;font-size:40px;" />
						</h2>
					</div>
				</div>

				<div class="row " align="center">
					<rich:spacer height="30px"></rich:spacer>
				</div>

				<div class="row col-md-12" align="center"
					style="BACKGROUND-COLOR: #dee0e2; ">
					<div class="col-md-12" align="center">
						<h:selectOneRadio style="FONT-WEIGHT: bold;  font-size: 15px; "
							value="#{dispatchReportBWFL_FL2DAction.radio}"
							valueChangeListener="#{dispatchReportBWFL_FL2DAction.radioListener}"
							onchange="this.form.submit();" >
							<f:selectItem itemValue="CD" itemLabel="Consolidated" />
							<f:selectItem itemValue="BFW" itemLabel="BWFL / FL2D Wise" />
							<f:selectItem itemValue="SBW" itemLabel="Selected BWFL" />
							<f:selectItem itemValue="SFL" itemLabel="Selected FL2D" />							
						</h:selectOneRadio>
						
						<h:selectOneMenu style="width: 150px" rendered="#{dispatchReportBWFL_FL2DAction.radio eq 'SBW'}"
							value="#{dispatchReportBWFL_FL2DAction.drpdown}"
							valueChangeListener="#{dispatchReportBWFL_FL2DAction.chngval}"
							onchange="this.form.submit();">

							<f:selectItem itemValue="Select" itemLabel="-Select-" />
							<f:selectItem itemValue="BWFL2A" itemLabel="BWFL2A" />
							<f:selectItem itemValue="BWFL2B" itemLabel="BWFL2B" />
							<f:selectItem itemValue="BWFL2C" itemLabel="BWFL2C" />
							<f:selectItem itemValue="BWFL2D" itemLabel="BWFL2D" />
							
						</h:selectOneMenu>
						
					</div>
					

				</div>
				
				
				<div class="row">
					<rich:spacer height="20px"></rich:spacer>
				</div>

				<div class="col-md-12" align="center">
					<div class="col-md-3" align="right">
						<h:outputText value="Select BWFL Name:" rendered="#{dispatchReportBWFL_FL2DAction.drpFlg}"
							style="FONT-SIZE: small; FONT-WEIGHT: bold;"></h:outputText>
					</div>
					<div class="col-md-3" align="left">
						<h:selectOneMenu rendered="#{dispatchReportBWFL_FL2DAction.drpFlg}"
							value="#{dispatchReportBWFL_FL2DAction.bwflId}"
							style="width: 250px; height: 28px;">
							<f:selectItems
								value="#{dispatchReportBWFL_FL2DAction.bwflList}" />
						</h:selectOneMenu>
					</div>
					<div class="col-md-3" align="right">
						<h:outputText value="Select FL2D Name:" rendered="#{dispatchReportBWFL_FL2DAction.radio eq 'SFL'}"
							style="FONT-SIZE: small; FONT-WEIGHT: bold;"></h:outputText>
					</div>
					<div class="col-md-3" align="left">
						<h:selectOneMenu rendered="#{dispatchReportBWFL_FL2DAction.radio eq 'SFL'}"
							value="#{dispatchReportBWFL_FL2DAction.fl2DId}"
							style="width: 250px; height: 28px;">
							<f:selectItems
								value="#{dispatchReportBWFL_FL2DAction.fl2DList}" />
						</h:selectOneMenu>
					</div>
					
				</div>

				<div class="row">
					<rich:spacer height="20px"></rich:spacer>
				</div>

				<div class="col-md=12" align="center">
					<div class="col-md-3"></div>
					<div class="col-md-1" align="right">From:</div>
					<div class="col-md-2" align="left">
						<rich:calendar value="#{dispatchReportBWFL_FL2DAction.fromDate}">
						</rich:calendar>
					</div>
					<div class="col-md-1" align="right">To:</div>
					<div class="col-md-2" align="left">
						<rich:calendar value="#{dispatchReportBWFL_FL2DAction.toDate}">
						</rich:calendar>
					</div>
					<div class="col-md-3"></div>
				</div>

				<div class="row " align="center">
					<rich:spacer height="30px"></rich:spacer>
				</div>
				<br /> <br />
				<div class="row" align="center">
					<h:commandButton value="Print Report"
						action="#{dispatchReportBWFL_FL2DAction.print}">
					</h:commandButton>

					<h:outputLink styleClass="outputLinkEx"
						value="/doc/ExciseUp/MIS/pdf/#{dispatchReportBWFL_FL2DAction.pdfname}"
						target="_blank">
						<h:outputText styleClass="outputText" id="text223"
							value="View Report"
							rendered="#{dispatchReportBWFL_FL2DAction.printFlag==true}"
							style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
					</h:outputLink>

					<rich:spacer width="10px;"></rich:spacer>
					<h:commandButton value="Reset"
						action="#{dispatchReportBWFL_FL2DAction.reset}">
					</h:commandButton>
				</div>
				<br />
				<div class="col-md-12" align="center">
					<h:commandButton action="#{dispatchReportBWFL_FL2DAction.excel}"
						value="Generate Excel" rendered="true" />

					<h:outputLink styleClass="outputLinkEx"
						value="/doc/ExciseUp//MIS//Excel//#{dispatchReportBWFL_FL2DAction.exlname}.xls"
						target="_blank">
						<h:outputText styleClass="outputText" value="Download Excel"
							rendered="#{dispatchReportBWFL_FL2DAction.excelFlag==true}"
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