 <ui:composition
       xmlns="http://www.w3.org/1999/xhtml"
      xmlns:f="http://java.sun.com/jsf/core"
      xmlns:ui="http://java.sun.com/jsf/facelets"
      xmlns:a4j="http://richfaces.org/a4j"
	  xmlns:rich="http://richfaces.org/rich"
      xmlns:h="http://java.sun.com/jsf/html">
      
    <f:view>
           <h:form>
                   <head>
                         <style type="text/css">
                         .heading{
                           font-size:3em;
                           margin-top : 1em;
                           COLOR: #6A5ACD; 
                           letter-spacing:10px;                                                              
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
                         padding-top: 60px ;
                         }
                      .dropdown-menu{
                         box-shadow: 10px 5px 5px red;
                         }
                         </style>                 
                   </head>
   
    <div class="panel panel-default form-group ">
    <div class="heading" align="center"><h:outputLabel style="FONT-FAMILY: 'Adobe Fan Heiti Std B';">HeadWise    Challan    Detail</h:outputLabel></div>
    <div class="row" align="center">
					<a4j:outputPanel id="msg">
						<div class="row col-md-12 wow shake" style="margin-top: 2px;">
							<h:messages errorStyle="color:red"
								style="font-size: 14px;font-weight: bold"
								styleClass="generalExciseStyle" layout="table"
								infoStyle="color:green" />
						</div>
					</a4j:outputPanel>
				</div>
      <rich:spacer height="20px"></rich:spacer>
      
      <div class="paneldata">
     <div class="col-md-12 row date" align="center">
		     <h:outputLabel>From Date  </h:outputLabel>
		     <rich:calendar  value="#{headWise_challan_detail_action.from_date}"></rich:calendar>
		     <rich:spacer width="100px"></rich:spacer>
		      <h:outputLabel>To Date  </h:outputLabel>    
		      <rich:calendar value="#{headWise_challan_detail_action.to_date}"></rich:calendar>   
     </div>
     <rich:spacer height="40px"></rich:spacer>
     <div class="col-md-12 row dpdown" align="center">
		     
		     <h:outputLabel>Challan Head</h:outputLabel>
		     <h:selectOneMenu style="width: 30%; height: 28px;margin-left:10px;color:#663399"
									value="#{headWise_challan_detail_action.challanHeadId}"
					valueChangeListener ="#{headWise_challan_detail_action.challan_change}"
					 onchange="this.form.submit()"	
									styleClass="generalExciseStyle">							
									<f:selectItems value="#{headWise_challan_detail_action.challanHeadList}" />
								</h:selectOneMenu>
		    
		     <rich:spacer width="100px"></rich:spacer>
		    
		      

		     <h:outputLabel>G6 Head   </h:outputLabel>
				<h:selectOneMenu
						value="#{headWise_challan_detail_action.g6HeadId}"  onchange="this.form.submit()"
						style="height: 28px; width : 30%;COLOR: #663399;margin-left:10px;" 
						valueChangeListener="#{headWise_challan_detail_action.g6_challan_listner}">
						<f:selectItems value="#{headWise_challan_detail_action.g6HeadList}"/>
					</h:selectOneMenu>	
					
		    
     </div>
     
          
     
    <div class="printbutton" align="center">
    
    <rich:spacer height="40px"></rich:spacer>
					<h:commandButton value="Print Report" styleClass="btn btn-success"
					    style ="border-radius:10px"
						action="#{headWise_challan_detail_action.print}">
					</h:commandButton>

					<h:outputLink styleClass="outputLinkEx"
						value="/doc/ExciseUp/MIS/pdf/#{headWise_challan_detail_action.pdfName}"
						target="_blank">
						<h:outputText styleClass="outputText" id="text223"
							value="View Report"
							rendered="#{headWise_challan_detail_action.printFlag==true}"
							style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
					</h:outputLink>
					<rich:spacer width="20px;"></rich:spacer>
					<h:commandButton value="Excel " styleClass="btn btn-primary"
					    style ="border-radius:10px"
						action="#{headWise_challan_detail_action.excel}">
					</h:commandButton>
					<h:outputLink styleClass="outputLinkEx"
							value="/doc/ExciseUp//MIS//Excel//#{headWise_challan_detail_action.exlname}"
							target="_blank">
							<h:outputText styleClass="outputText" value="Download Excel"
								rendered="#{headWise_challan_detail_action.excelFlag==true}"
								style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
						</h:outputLink>
					<rich:spacer width="20px;"></rich:spacer>
					<h:commandButton value="Reset" styleClass="btn btn-warning"
					      style ="border-radius:10px"
						action="#{headWise_challan_detail_action.reset}">
					</h:commandButton>
				</div>
     
     
                 </div>
       
          </div>              
          </h:form>   
    </f:view>  
</ui:composition>