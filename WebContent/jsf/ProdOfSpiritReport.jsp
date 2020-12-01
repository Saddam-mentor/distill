<ui:composition xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:a4j="http://richfaces.org/a4j"
	xmlns:rich="http://richfaces.org/rich">


	<h:form>



		<f:view>
			<div class="form-group">
				<div class="row " align="center">
					<rich:spacer height="30px"></rich:spacer>
				</div>
				<div>
					<a4j:outputPanel id="msg">
						<h:messages errorStyle="color:red"
							style="font-size: 14px;font-weight: bold"
							styleClass="generalExciseStyle" layout="table" id="messages"
							infoStyle="color:green">
						</h:messages>
					</a4j:outputPanel>
				</div>

				<div class="row " align="center">
					<div>
						<h2>
							<h:outputText value="Date Wise Production Of Spirit   "
								styleClass="generalHeaderOutputTable"
								style="font-weight: bold; font-size: 40px;text-decoration: underline;margin-top: 2px;font-family:Monotype Corsiva;font-size:40px;">
							</h:outputText>
											
							
						</h2>
					</div>
				</div>

				<div class="form-group newsdiv">
					<div class=" row col-md-12">
						<rich:spacer height="20px" />
					</div>

					

					<div class=" row col-md-12">
						<rich:spacer height="20px" />
					</div>
					
					<div class="col-md-12" align="center">
											<h:selectOneRadio
												value="#{prodOfSpiritReportAction.radio}"
												valueChangeListener="#{prodOfSpiritReportAction.datalist}"
												onchange="this.form.submit();">

												<f:selectItem itemValue="S"
													itemLabel="Summary" />
												<f:selectItem itemValue="D"
													itemLabel=" Detail " />
												
	                                    </h:selectOneRadio>
										</div>
<rich:spacer height="20px"></rich:spacer>
					<div class="row col-md-12" align="center">
						Between Dates :
						<rich:calendar
							value="#{prodOfSpiritReportAction.fromdate}"></rich:calendar>
						and :
						<rich:calendar value="#{prodOfSpiritReportAction.todate}"></rich:calendar>
					</div>
					
					<div class="col-md-12" align="center">
										<rich:spacer height="80px" />
										
						<h:outputLabel value="Select Distillery : " rendered="#{prodOfSpiritReportAction.radio eq 'D'}"></h:outputLabel>				 
					
					<h:selectOneMenu value="#{prodOfSpiritReportAction.dist_id}" rendered="#{prodOfSpiritReportAction.radio eq 'D'}"
											onchange="this.form.submit();"  style="width:300px">
											<f:selectItems
												value="#{prodOfSpiritReportAction.dist_list}" />
										</h:selectOneMenu>
					</div>
					<rich:spacer height="20px" />

					

					<div class="row ">
						<rich:spacer height="30px" />
					</div>


					<div class="col-md-12" align="center">


						<h:commandButton
							action="#{prodOfSpiritReportAction.print}" value="Print Report"
							style="background-color:#C5C5C5; font-size: large;" />

						<h:outputLink styleClass="outputLinkEx"
							value="/doc/ExciseUp/WholeSale/pdf/#{prodOfSpiritReportAction.pdfname}"
							target="_blank">
							<h:outputText styleClass="outputText" id="text223"
								value="View Report"
								rendered="#{prodOfSpiritReportAction.printFlag}"
								style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
						</h:outputLink>



					</div>
					<div class="row " align="center">
						<rich:spacer height="30px"></rich:spacer>
					</div>
					<div class="col-md-12" align="center">


						<h:commandButton
							action="#{prodOfSpiritReportAction.excel}"
							value="Generate Excel"
							style="background-color:#C5C5C5; font-size: large;"
							rendered="false" />

						<h:outputLink
						rendered="false" styleClass="outputLinkEx"
							
							value="/doc/ExciseUp//WholeSale//excel//#{prodOfSpiritReportAction.exlname}.xls"
							target="_blank">
							<h:outputText styleClass="outputText" value="Download Excel"
								rendered="#{prodOfSpiritReportAction.excelFlag==true}"
								style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
						</h:outputLink>



					</div>
					<div class="row " align="center">
						<rich:spacer height="30px"></rich:spacer>
					</div>
					<div class="col-md-12" align="center">





						<h:commandButton
							action="#{prodOfSpiritReportAction.reset}" value="Reset"
							style="background-color:#C5C5C5; font-size: large; width: 110px;" />

					</div>
					<rich:spacer height="20px" />
				</div>
			</div>
		</f:view>
	</h:form>
</ui:composition>