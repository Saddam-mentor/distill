<ui:composition xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:a4j="http://richfaces.org/a4j"
	xmlns:rich="http://richfaces.org/rich">
	<h:form>

		<f:view>
			<div class="form-group">
				<div class="form-group" style="FONT-FAMILY: 'Arial';">
					<div class="row ">
						<rich:spacer height="15px;" />
					</div>
					<div>
						<a4j:outputPanel id="msg">
							<div class="row wow shake">
								<h:messages errorStyle="color:red"
									style="font-size: 14px;font-weight: bold"
									styleClass="generalExciseStyle" layout="table" id="messages"
									infoStyle="color:green" />
							</div>
						</a4j:outputPanel>
					</div>

					<br /> <br />
					<div class="row " align="center">
						<div class="pghead">
							<h1>
								<h:outputText value="Indent Status" />
							</h1>
						</div>
					</div>


					<rich:spacer height="20px" />





					<div class="row col-md-12" align="center"
						style="BACKGROUND-COLOR: #dee0e2;">
						<div class="col-md-12" align="center">
							<h:selectOneRadio value="#{indentStatusAction.radioCheck}"
								valueChangeListener="#{indentStatusAction.chngval}"
								onchange="this.form.submit();">

								<f:selectItem itemValue="D" itemLabel="Distilleries" />
								<f:selectItem itemValue="B" itemLabel="Brewery" />

							</h:selectOneRadio>
						</div>

					</div>

				</div>

				<!--  -->
				<br /> <br />
				<rich:spacer height="40px" />

				<br />
				<!--  -->
				<rich:spacer height="20px" />


				<br />


				<div class="col-md-12" align="center">

					<h:commandButton action="#{indentStatusAction.savePdf}"
						value="Print Report" />

					<h:outputLink styleClass="outputLinkEx"
						value="/doc/ExciseUp/reports/#{indentStatusAction.pdfname}"
						target="_blank">
						<h:outputText styleClass="outputText" id="text223"
							value="View Report"
							rendered="#{indentStatusAction.printFlag==true}"
							style="color: blue; font-family: serif; font-size: 10pt"></h:outputText>
					</h:outputLink>

				</div>

				<br />






				<div class="row" align="center">
					<div class="col-md-12" style="FONT-FAMILY: 'Agency FB';"></div>
				</div>
			</div>
		</f:view>
	</h:form>
</ui:composition>