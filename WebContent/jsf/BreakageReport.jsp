 <ui:composition xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:a4j="http://richfaces.org/a4j"
	xmlns:rich="http://richfaces.org/rich">

   <f:view>

		<h:form>

			<TABLE width="80%">
				<TBODY>
					<TR>
						<TD align="left"><h3>
								<h:messages errorStyle="color:red" layout="table" id="messages"
									infoStyle="color:green" />
							</h3></TD>
					</TR>
				</TBODY>
			</TABLE>
       
                <div class="row " align="center">
				<rich:spacer height="10px"></rich:spacer>
			</div>
			<div class="row">
				<div align="center">
					<h:outputText value=" Report On Plan/Bottled/Boxed Cases."
						style="COLOR: #0000a0; FONT-WEIGHT: bold; FONT-SIZE: 35px;font-family:Comic Sans MS;"></h:outputText>
					<h:inputHidden value="#{breakageReportaction.hidden}"></h:inputHidden>
					 <hr style="border-top: 7px #D0D3D4; border-top-style: dashed;"></hr>
				</div>
			</div>
						<div  class="row " align="center"><rich:spacer height="20px"></rich:spacer></div>
			
            <div class="row" align="center">
					<h:selectOneRadio value="#{breakageReportaction.radio}"
						onchange="this.form.submit();"
						valueChangeListener="#{breakageReportaction.radioListener}">
						<f:selectItem itemValue="D" itemLabel=" Distillery" />
						<f:selectItem 	 itemValue="B" itemLabel="Brewery"   itemDisabled="#{breakageReportaction.hideFlag}"/>
						<f:selectItem 	itemValue="AD" itemLabel=" All Distillery"  itemDisabled="#{breakageReportaction.hideFlag}" />
						<f:selectItem 	 itemValue="AB" itemLabel="All Brewery"   itemDisabled="#{breakageReportaction.hideFlag}" />
						 
					</h:selectOneRadio>
					
				</div>
				<div  class="row " align="center"><rich:spacer height="20px"></rich:spacer></div>
				<div>
				<div class="row" align="center">
				<h:outputText value=" Unit Name:-"     rendered="#{breakageReportaction.radio  eq 'D' or breakageReportaction.radio  eq 'B'}"/>
				
					<h:selectOneMenu
					
															    rendered="#{breakageReportaction.radio  eq 'D' or breakageReportaction.radio  eq 'B'}"
																disabled="#{breakageReportaction.hideFlag}"
																value="#{breakageReportaction.unit_id}" onchange="this.form.submit()"
																valueChangeListener="#{breakageReportaction.change_List}"
																style="height: 28px; width : 213px;" >
																<f:selectItems
																	value="#{breakageReportaction.unit_List}" />
															</h:selectOneMenu>
															</div>
															</div>
															<div  class="row " align="center"><rich:spacer height="20px">
															</rich:spacer>
															
															</div>
															
															
															
				<div class="row" align="center">
					<h:selectOneRadio value="#{breakageReportaction.radio2}"
						onchange="this.form.submit();"
						valueChangeListener="#{breakageReportaction.radioListener1}">
						 
						 
						<f:selectItem itemValue="S" itemLabel="Summary" />
						<f:selectItem itemValue="DL" itemLabel="Detail" />
						 
						 
					</h:selectOneRadio>
				</div>
				<div  class="row " align="center"><rich:spacer height="20px">
															</rich:spacer>
															
															</div>
<h:panelGroup rendered="#{breakageReportaction.radio2 eq 'DL'}">
				<div class="col-md=12" align="center">
                 <h:outputText style="FONT-SIZE: 15px; FONT-WEIGHT: bold;"
				  value="Plan Date:"></h:outputText>
                    <rich:calendar value="#{breakageReportaction.fromDate}">
					</rich:calendar>
				</div>
				</h:panelGroup>

          <div  class="row " align="center"><rich:spacer height="30px"></rich:spacer></div>
				
				<div class="row" align="center">
					<h:commandButton  styleClass="btn btn-success" value="Print Report" action="#{breakageReportaction.print}">
					</h:commandButton>
					
					<h:outputLink styleClass="outputLinkEx" value="/doc/ExciseUp/MIS/pdf/#{breakageReportaction.pdfname}"
									target="_blank">
					<h:outputText styleClass="outputText" id="text223" value="View Report"
					rendered="#{breakageReportaction.printFlag==true}"
					style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
					</h:outputLink>
				<h:commandButton value="Reset" styleClass="btn btn-warning"
						action="#{breakageReportaction.reset}">
					</h:commandButton>
				</div>	
				
			<div  class="row " align="center"><rich:spacer height="30px"></rich:spacer></div>
		
       </h:form>

    </f:view>
          			
</ui:composition>