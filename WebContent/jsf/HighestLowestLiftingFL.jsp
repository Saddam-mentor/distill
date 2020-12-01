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
							<h:outputText value="Highest and Lowest Lifting "
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
							style="FONT-WEIGHT: bold;  font-size: 18px; width: 25%; "
							value="#{cL_MCQ_Shop_Cummulative_FilteredAction.fl_cl}"
						valueChangeListener="#{cL_MCQ_Shop_Cummulative_FilteredAction.sortedListener}">
							<f:selectItem itemValue="CL" itemLabel="CL Lifting" />
							<f:selectItem itemValue="FL" itemLabel="FL Lifting " />
						</h:selectOneRadio>
					</div>
				</div>
				<div class="row col-md-12" align="center"
					style="BACKGROUND-COLOR: #dee0e2;">
					<div class="col-md-12" align="center">
						<h:selectOneRadio
							style="FONT-WEIGHT: bold;  font-size: 18px; width: 20%; "
							value="#{cL_MCQ_Shop_Cummulative_FilteredAction.sorted}"
							valueChangeListener="#{cL_MCQ_Shop_Cummulative_FilteredAction.sortedListener}"
							onchange="this.form.submit();">
							<f:selectItem itemValue="H" itemLabel="Highest" />
							<f:selectItem itemValue="L" itemLabel="Lowest" />
							

						</h:selectOneRadio>
					</div>
				</div>

				<div class="row col-md-12" align="center"
					style="BACKGROUND-COLOR: #dee0e2;">
					
				</div>

				<div class="row">
					<rich:spacer height="20px"></rich:spacer>
				</div>

					<div class="row col-md-12" align="center">
				<h:outputText  >
					 District :</h:outputText>
					 
					 	<h:selectOneMenu
						
						value="#{cL_MCQ_Shop_Cummulative_FilteredAction.distid}"
						 valueChangeListener="#{cL_MCQ_Shop_Cummulative_FilteredAction.radioListener}"
						 onchange="this.form.submit();"
						style="height: 28px; width : 120px;" 	>
						<f:selectItems value="#{cL_MCQ_Shop_Cummulative_FilteredAction.districtList}" />
					</h:selectOneMenu>
				
					<rich:spacer width="30px"></rich:spacer>
					
					<h:outputText value="Month*:"
						styleClass="generalExciseStyle" ></h:outputText>
						
						
					<h:selectOneMenu 
						style="width: 250px; height: 28px;"
					
						value="#{cL_MCQ_Shop_Cummulative_FilteredAction.monthId}" 
						 valueChangeListener="#{cL_MCQ_Shop_Cummulative_FilteredAction.radioListenerMonth}"
						 onchange="this.form.submit();"
						>
						
						<f:selectItem itemLabel="January" itemValue="1" />
						<f:selectItem itemLabel="Feburary" itemValue="2" />
						<f:selectItem itemLabel="March" itemValue="3" />
						<f:selectItem itemLabel="April" itemValue="4" />
						<f:selectItem itemLabel="May" itemValue="5" />
						<f:selectItem itemLabel="June" itemValue="6" />
						<f:selectItem itemLabel="July" itemValue="7" />
						<f:selectItem itemLabel="August" itemValue="8" />
						<f:selectItem itemLabel="September" itemValue="9" />
						<f:selectItem itemLabel="October" itemValue="10" />
						<f:selectItem itemLabel="November" itemValue="11" />
						<f:selectItem itemLabel="December" itemValue="12" />
					</h:selectOneMenu>
				
					
					</div>

				<div class="row " align="center">
					
				</div>
				<br /> <br />
				<div class="row" align="center">
					<h:commandButton value="Print Report"
				
					styleClass="btn btn-success"
						action="#{cL_MCQ_Shop_Cummulative_FilteredAction.print}">
					</h:commandButton>

					<h:outputLink styleClass="outputLinkEx"
					
						value="/doc/ExciseUp/MIS/pdf/#{cL_MCQ_Shop_Cummulative_FilteredAction.pdfName}"
						target="_blank">
						<h:outputText styleClass="outputText" id="text223"
							value="View Report"
							rendered="#{cL_MCQ_Shop_Cummulative_FilteredAction.printFlag==true}"
							style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
					</h:outputLink>

					
				</div>
				
				<div class="col-md-12" align="center">


						<h:commandButton 
							action="#{cL_MCQ_Shop_Cummulative_FilteredAction.excel}"
							value="Generate Excel"
							style="background-color:#C5C5C5; font-size: large;"
							 />

						<h:outputLink styleClass="outputLinkEx"
							value="/doc/ExciseUp//MIS//Excel//#{cL_MCQ_Shop_Cummulative_FilteredAction.exlname}CL_MGQ_Achievement.xls"
							target="_blank">
							<h:outputText styleClass="outputText" value="Download Excel"
								rendered="#{cL_MCQ_Shop_Cummulative_FilteredAction.excelFlag==true}"
								style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
						</h:outputLink>



					</div>
				
				
				<div 
				class="row" align="center">
					

					

					
				</div>
				<br />
				<div class="col-md-12" align="center">
					
<rich:spacer width="10px;"></rich:spacer>
					<h:commandButton value="Reset" styleClass="btn btn-warning"
						action="#{cL_MCQ_Shop_Cummulative_FilteredAction.reset}">
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