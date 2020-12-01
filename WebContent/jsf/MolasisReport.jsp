<ui:composition xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:a4j="http://richfaces.org/a4j"
	xmlns:rich="http://richfaces.org/rich">

	<f:view>

		<h:form>

			<div class="form-group">

				<hr style="border-top: 7px #D0D3D4; border-top-style: dashed;"></hr>

				<div align="center">

					<h:outputText value="Molasses Report"
						style="font-size: 40px;font-style: italic bold;color:black;"></h:outputText>

					<hr style="border-top: 7px #D0D3D4; border-top-style: dashed;"></hr>
				</div>

				<div width="50%" align="center"
					style="background-color: #D0D3D4; padding: 5;">
					<div>
						<rich:messages showSummary="true"
							style="color:red; font-family:Monotype Corsiva; font-size: 20px;"></rich:messages>
					</div>


					<rich:spacer height="10px"></rich:spacer>




					<div class="col-md-12" align="center">
						<h:selectOneRadio style="padding:5px;"
							value="#{molasisReportAction.radioh}"
							valueChangeListener="#{molasisReportAction.radix}"
							onchange="this.form.submit();">
							<f:selectItem itemLabel="B-Heavy" itemValue="B" />
							<f:selectItem itemLabel="C-Heavy" itemValue="C" />

						</h:selectOneRadio> 
					
				 
					</div>
					<div class="col-md-12" align="center">
						<h:selectOneMenu 
							value="#{molasisReportAction.season}">
							<f:selectItems
								value="#{molasisReportAction.seasonList}" />
						</h:selectOneMenu>
					</div>

					<div class="row " align="center">
						<rich:spacer height="10px"></rich:spacer>
					</div>
					
					<div class="col-md-12" align="center">
						<h:selectOneRadio style="padding:30px;"
							value="#{molasisReportAction.radio}"
							valueChangeListener="#{molasisReportAction.radix}"
							onchange="this.form.submit();">
							<f:selectItem itemLabel="Distillery With In State" itemValue="D" />
							<f:selectItem itemLabel="Industry With In State" itemValue="I"
								itemDisabled="#{molasisReportAction.radioh eq 'B'}" />
							<f:selectItem itemLabel="Distillery Outside State"
								itemValue="DOUP"
								itemDisabled="#{molasisReportAction.radioh eq 'B'}" />
							<f:selectItem itemLabel="Industry Outside State" itemValue="IOUP"
								itemDisabled="#{molasisReportAction.radioh eq 'B'}" />
						</h:selectOneRadio>
					</div>
					<br /> <br />
					<div class="row">
						<div class="col-md-3"></div>
						<div class="col-md-3" align="right">
							<h:outputText value="SelectUnitName">
							</h:outputText>
						</div>
						<div class="col-md-3" align="left">
							<h:selectOneMenu value="#{molasisReportAction.selectDistillery}"
								rendered="#{molasisReportAction.radio eq 'D'}"
								style="width:50%;padding: 8px;">
								<f:selectItems value="#{molasisReportAction.selectmenu}" />
							</h:selectOneMenu>


							<h:selectOneMenu value="#{molasisReportAction.selectIndustry}"
								rendered="#{molasisReportAction.radio eq 'I'}"
								style="width:50%;padding: 8px;">
								<f:selectItems value="#{molasisReportAction.selectmenu}" />
							</h:selectOneMenu>


							<h:selectOneMenu value="#{molasisReportAction.selectDOUP}"
								rendered="#{molasisReportAction.radio eq 'DOUP'}"
								style="width:50%;padding: 8px;">
								<f:selectItems value="#{molasisReportAction.selectmenu}" />
							</h:selectOneMenu>
							<h:selectOneMenu value="#{molasisReportAction.selectIOUP}"
								rendered="#{molasisReportAction.radio eq 'IOUP'}"
								style="width:50%;padding: 8px;">
								<f:selectItems value="#{molasisReportAction.selectmenu}" />
							</h:selectOneMenu>
						</div>
						<div class="col-md-3"></div>
					</div>
					<div class="row " align="center">
						<rich:spacer height="30px"></rich:spacer>
					</div>

					<div class="row" align="center">
						<div class="col-md-2"></div>
						<div class="col-md-2" align="right">
							<h:outputText value="FromDate"></h:outputText>
						</div>
						<div class="col-md-2">
							<rich:calendar value="#{molasisReportAction.fromDate}"></rich:calendar>
						</div>
						<div class="col-md-1" align="right">
							<h:outputText value="ToDate"></h:outputText>

						</div>
						<div class="col-md-2" align="left">
							<rich:calendar value="#{molasisReportAction.toDate}"></rich:calendar>
						</div>

						<div class="col-md-3"></div>
					</div>


					<br></br> <br></br> <br></br>
					<div class="row">
						<div align="center">
							<h:commandButton value="PrintReport"
								action="#{molasisReportAction.printReport}"
								styleClass="btn btn-primary"></h:commandButton>

							<h:outputLink styleClass="outputLinkEx"
								value="/doc/ExciseUp/MIS/pdf/#{molasisReportAction.pdfName}"
								target="_blank">
								<h:outputText styleClass="outputText" id="text223"
									value="View Report"
									rendered="#{molasisReportAction.printFlag==true}"
									style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
							</h:outputLink>
							<h:commandButton value="Reset"
								action="#{molasisReportAction.reset}"
								styleClass="btn btn-danger"></h:commandButton>
						</div>
					</div>



					<div>
						<rich:spacer height="20px;"></rich:spacer>
					</div>

				</div>



			</div>

		</h:form>

	</f:view>



</ui:composition>