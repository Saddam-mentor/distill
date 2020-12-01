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
							<h:outputText value=" Stock At Wholesale "
								styleClass="generalHeaderOutputTable"
								style="font-weight: bold; font-size: 40px;text-decoration: underline;margin-top: 2px;font-family:Monotype Corsiva;font-size:40px;" />
						</h2>
					</div>
				</div>

				<div class="row " align="center">
					<rich:spacer height="30px"></rich:spacer>
				</div>

				<div class="row col-md-12" align="center"
					style="BACKGROUND-COLOR: #dee0e2;">
					<div class="col-md-12" align="center">
						<h:selectOneRadio
							style="FONT-WEIGHT: bold;  font-size: 16px; width: 20%; "
							value="#{wholesaleStockAction.radio}"
							valueChangeListener="#{wholesaleStockAction.radioListener}"
							onchange="this.form.submit();">
							<f:selectItem itemValue="CD" itemLabel="Consolidated" />
							<f:selectItem itemValue="LW" itemLabel="License Wise" />

						</h:selectOneRadio>



					</div>


				</div>


				<div class="row">
					<rich:spacer height="20px"></rich:spacer>
				</div>



				<div class="row">
					<rich:spacer height="20px"></rich:spacer>
				</div>

				<div class="col-md=12" align="center">
					<div class="col-md-2"></div>
					<div class="col-md-1" align="right">
						<h:outputText value="District:"
							rendered="#{wholesaleStockAction.radio eq 'LW'}"
							style="FONT-SIZE:small; FONT-WEIGHT: bold;"></h:outputText>
					</div>
					<div class="col-md-2" align="left">
						<h:selectOneMenu value="#{wholesaleStockAction.district}"
							rendered="#{wholesaleStockAction.radio eq 'LW'}"
							style="width: 150px; height: 25px;">
							<f:selectItems value="#{wholesaleStockAction.districtList}" />
						</h:selectOneMenu>
					</div>
					<div class="col-md-1" align="right">
						<h:outputText value="Date:"
							style="FONT-SIZE:small; FONT-WEIGHT: bold;"></h:outputText>
					</div>
					<div class="col-md-2" align="left">
						<rich:calendar value="#{wholesaleStockAction.dtDate}">
						</rich:calendar>
					</div>

					<div class="col-md-1" align="right">
						<h:outputText value="Type:"
							rendered="#{wholesaleStockAction.radio eq 'LW'}"
							style="FONT-SIZE:small; FONT-WEIGHT: bold;"></h:outputText>
					</div>
					<div class="col-md-2" align="left">
						<h:selectOneMenu style="width: 150px; height: 25px;"
							rendered="#{wholesaleStockAction.radio eq 'LW'}"
							value="#{wholesaleStockAction.type}">

							<f:selectItem itemValue="Select" itemLabel="-Select-" />
							<f:selectItem itemValue="CL2" itemLabel="CL2" />
							<f:selectItem itemValue="FL2" itemLabel="FL2" />
							<f:selectItem itemValue="FL2B" itemLabel="FL2B" />


						</h:selectOneMenu>
					</div>
					<div class="col-md-1"></div>
				</div>

				<div class="row " align="center">
					<rich:spacer height="30px"></rich:spacer>
				</div>
				<br /> <br />
				<div class="row" align="center">
					<h:commandButton value="Print Report" styleClass="btn btn-success"
						action="#{wholesaleStockAction.print}">
					</h:commandButton>

					<h:outputLink styleClass="outputLinkEx"
						value="/doc/ExciseUp/MIS/pdf/#{wholesaleStockAction.pdfname}"
						target="_blank">
						<h:outputText styleClass="outputText" id="text223"
							value="View Report"
							rendered="#{wholesaleStockAction.printFlag==true}"
							style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
					</h:outputLink>

					<rich:spacer width="10px;"></rich:spacer>
					<h:commandButton value="Reset" styleClass="btn btn-warning"
						action="#{wholesaleStockAction.reset}">
					</h:commandButton>
				</div>
				<br />
				<div class="col-md-12" align="center">
					<h:commandButton action="#{wholesaleStockAction.excel}"
						styleClass="btn btn-primary" value="Generate Excel"
						rendered="true" />

					<h:outputLink styleClass="outputLinkEx"
						value="/doc/ExciseUp//MIS//Excel//#{wholesaleStockAction.exlname}.xls"
						target="_blank">
						<h:outputText styleClass="outputText" value="Download Excel"
							rendered="#{wholesaleStockAction.excelFlag==true}"
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