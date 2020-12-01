<ui:composition xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:a4j="http://richfaces.org/a4j"
	xmlns:rich="http://richfaces.org/rich">
	<f:view>


		<h:form>
		<head>
                         <style type="text/css">
                         .heading{
                           font-size:2em;
                           margin-top : 1em;
                           COLOR: #6A5ACD; 
                           letter-spacing:10px; 
                          FONT-WEIGHT: bold;                                                           
                         }                       
                         .submit{
                             color:lightgreen;
                         }
                         .paneldata{
                            border: 4px #778899 solid ;
                            margin : 1em 10em;
                            padding :50px 10px ;                       
                              box-shadow: 
								           inset 0 -3em 3em rgba(0,0,0,0.1), 
								             0 0  0 2px rgb(255,255,255),
								             0.3em 0.3em 1em rgba(0,0,0,0.3);
								            
                            
                         }
                         .printbutton{
                         margin-top: 30px;
                         padding-top: 5px ;
                         }
                      .dropdown-menu{
                         box-shadow: 10px 5px 5px red;
                         }
                         
                         hr.style-one {
border: 0;
height: 1px;
width :80% ;
background: #333;
background-image: -webkit-linear-gradient(left, #ccc, #333, #ccc);
background-image: -moz-linear-gradient(left, #ccc, #333, #ccc);
background-image: -ms-linear-gradient(left, #ccc, #333, #ccc);
background-image: -o-linear-gradient(left, #ccc, #333, #ccc);
}
                         </style> 
                                         
                   </head>
			<div class="panel panel-default">
				<div class="panel-body">
					<div class="row" align="left">
						<h:messages errorStyle="color:red" layout="table" id="messages"
							infoStyle="color:green">
						</h:messages>

					</div>
					<rich:spacer height="10px;"></rich:spacer>
					<div class="row heading" align="center">
						<h:outputText value="Report On S pecial Additional Consideration Fee"
							>
						</h:outputText>
					</div>
				<rich:spacer height="30px;"></rich:spacer>	
	<div class="paneldata">			
					<div class="row" align="center" style="FONT-FAMILY: 'Tahoma';">
						From Date :
						<rich:calendar value="#{reportOnSpclAdditinalConsidrationFeeAction.fromDate}"></rich:calendar>
						<rich:spacer width="10px;"></rich:spacer>
						To Date :
						<rich:calendar value="#{reportOnSpclAdditinalConsidrationFeeAction.toDate}"></rich:calendar>
					</div>
					<rich:spacer height="20px;"></rich:spacer>
					<div class="row" align="center">
						<h:selectOneRadio value="#{reportOnSpclAdditinalConsidrationFeeAction.radio}"
							style="FONT-FAMILY: 'Century Gothic'; width: 16%"
							valueChangeListener="#{reportOnSpclAdditinalConsidrationFeeAction.radioListnr}">
							<f:selectItem itemValue="S" itemLabel="Summary" />
							<f:selectItem itemValue="D" itemLabel="Detail" />
						</h:selectOneRadio>
					</div>
					<rich:spacer height="25px;"></rich:spacer>
					<h:panelGroup rendered="false">
						<div class="row" align="center">
							Type:
							<h:selectOneMenu value="#{reportOnSpclAdditinalConsidrationFeeAction.unitType}">
								<f:selectItem itemLabel="-----Select-----" itemValue="" />
								<f:selectItem itemLabel="Sugarmill" itemValue="S" />
								<f:selectItem itemLabel="Distillery" itemValue="D" />
								<f:selectItem itemLabel="Brewery" itemValue="B" />
								<f:selectItem itemLabel="BWFL" itemValue="BWFL" />
								<f:selectItem itemLabel="FL2D" itemValue="FL2D" />
								<f:selectItem itemLabel="Shop" itemValue="Shop" />
								<f:selectItem itemValue="HBR" itemLabel="HBR" />
								<f:selectItem itemLabel="Other" itemValue="Other" />
							</h:selectOneMenu>
						</div>
						<rich:spacer height="15px;"></rich:spacer>
					</h:panelGroup>
					<div class="row" align="center">
						<h:commandButton value="Print Report" styleClass="btn btn-success"
						style ="border-radius:10px"
							action="#{reportOnSpclAdditinalConsidrationFeeAction.printReport}">
						</h:commandButton>

						<h:outputLink styleClass="outputLinkEx"
							value="/doc/ExciseUp/MIS/pdf/#{reportOnSpclAdditinalConsidrationFeeAction.pdf_name}"
							target="_blank">
							<h:outputText styleClass="outputText" id="text223"
								value="View Report"
								rendered="#{reportOnSpclAdditinalConsidrationFeeAction.printFlag==true}"
								style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
						</h:outputLink>

						<rich:spacer width="10px;"></rich:spacer>
						<h:commandButton action="#{reportOnSpclAdditinalConsidrationFeeAction.excel}"
						style ="border-radius:10px"
							styleClass="btn btn-primary" value="Generate Excel"
							rendered="true" />

						<h:outputLink styleClass="outputLinkEx"
							value="/doc/ExciseUp//MIS//Excel//#{reportOnSpclAdditinalConsidrationFeeAction.exlname}.xls"
							target="_blank">
							<h:outputText styleClass="outputText" value="Download Excel"
								rendered="#{reportOnSpclAdditinalConsidrationFeeAction.excelFlag==true}"
								style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
						</h:outputLink>
						<rich:spacer width="10px;"></rich:spacer>
						<h:commandButton value="Reset" styleClass="btn btn-warning"
						style ="border-radius:10px"
							action="#{reportOnSpclAdditinalConsidrationFeeAction.reset}">
						</h:commandButton>
					</div>
					
					<div class="col-md-12" align="center">
						
					</div>
					<rich:spacer height="40px;"></rich:spacer>
					</div>
				</div>
			</div>
		</h:form>

	</f:view>
</ui:composition>
