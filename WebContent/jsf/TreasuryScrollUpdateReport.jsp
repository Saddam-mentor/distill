
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
                           font-size:3em;
                           margin-top : 1em;
                           COLOR: #6A5ACD; 
                           letter-spacing:2px;  
                           FONT-WEIGHT: bold;
                           font-family:'Adobe Fan Heiti Std B'                                                            
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

				<div style=" padding-bottom: 2px;"
					align="center" class="heading">
					
				<h:outputText value="Month Wise Treasury/Rajkosh Scroll Update Report" >
				</h:outputText>
	
				</div>
				
				<rich:spacer height="10px"></rich:spacer>
				<div class="row" align="center">
					<a4j:outputPanel id="msg">
						<div class="row col-md-12 wow shake" style="margin-top: 2px;margin-bottom: 2px;">
							<h:messages errorStyle="color:red"
								style="font-size: 14px;font-weight: bold"
								styleClass="generalExciseStyle" layout="table"
								infoStyle="color:green" />
						</div>
					</a4j:outputPanel>
				</div>
		

<div class="paneldata">

<div class="row" align="center">
<div class="col-md-12">
<h:selectOneRadio value="#{treasuryScrollUpdateReportAction.radio}" onchange="this.form.submit();"> 
<f:selectItem itemLabel="Deposit Month" itemValue="D"/>
<f:selectItem itemLabel="Treasury Update Month" itemValue="T"/>
<f:selectItem itemLabel="Koshwani Date" itemValue="K" itemDisabled="true"/>
</h:selectOneRadio>
</div>

</div>


 <rich:spacer height="15px"></rich:spacer>

<div class="row" align="center" >
<span class="col-md-5">
<h:outputText value="District"  style="FONT-SIZE: 100%; FONT-FAMILY: 'Lucida Sans Typewriter';">
</h:outputText></span>

<rich:spacer height="15px"></rich:spacer>

<span class="col-md-4">
<h:selectOneMenu value="#{treasuryScrollUpdateReportAction.district}" styleClass="form-control">
<f:selectItems value="#{treasuryScrollUpdateReportAction.districtList }"/>
</h:selectOneMenu>
</span>
</div>

<div class="row"> <rich:spacer height="15px"></rich:spacer></div>
<div class="row" align="center" >
<div class="col-md-3">
<h:outputText value="Select Year" style="FONT-FAMILY: 'Tahoma'; font-size:100%;"></h:outputText></div>
<div class="col-md-3"><h:selectOneMenu value="#{treasuryScrollUpdateReportAction.year}" styleClass="form-control">
<f:selectItem itemLabel="2018" itemValue="2018"/>
<f:selectItem itemLabel="2019" itemValue="2019"/>
<f:selectItem itemLabel="2020" itemValue="2020"/>
<f:selectItem itemLabel="2021" itemValue="2021"/>
</h:selectOneMenu></div>

<div class="col-md-3">
<h:outputText value="Select Month"   style="FONT-FAMILY: 'Tahoma'; font-size:100%;" rendered="#{!treasuryScrollUpdateReportAction.date_menu_render_flag }"></h:outputText>
<h:outputText value="Koshwani Date"   style="FONT-FAMILY: 'Tahoma'; font-size:100%;" rendered="#{treasuryScrollUpdateReportAction.date_menu_render_flag }"></h:outputText>
</div>
<div class="col-md-3">
<rich:calendar  value="#{treasuryScrollUpdateReportAction.date }" rendered="#{treasuryScrollUpdateReportAction.date_menu_render_flag }"></rich:calendar>

<h:selectOneMenu value="#{treasuryScrollUpdateReportAction.month }" styleClass="form-control" rendered="#{!treasuryScrollUpdateReportAction.date_menu_render_flag }">
<f:selectItem itemLabel="January" itemValue="01"/>
<f:selectItem itemLabel="Febrauary" itemValue="02"/>
<f:selectItem itemLabel="March" itemValue="03"/>
<f:selectItem itemLabel="April" itemValue="04"/>
<f:selectItem itemLabel="May" itemValue="05"/>
<f:selectItem itemLabel="June" itemValue="06"/>
<f:selectItem itemLabel="July" itemValue="07"/>
<f:selectItem itemLabel="August" itemValue="08"/>
<f:selectItem itemLabel="September" itemValue="09"/>
<f:selectItem itemLabel="October" itemValue="10"/>
<f:selectItem itemLabel="November" itemValue="11"/>
<f:selectItem itemLabel="December" itemValue="12"/>

</h:selectOneMenu>

</div>

</div>
 <rich:spacer height="30px"></rich:spacer>
<div class="row" align="center">


<h:commandButton value="Generate Excel" action="#{treasuryScrollUpdateReportAction.printReport}"
 style ="FONT-WEIGHT: bold; border-radius:10px"
 styleClass="btn btn-primary"> </h:commandButton>

<rich:spacer width="20px;"></rich:spacer>

<h:commandButton value="Reset" action="#{treasuryScrollUpdateReportAction.reset}" 
 style ="FONT-WEIGHT: bold; border-radius:10px" styleClass="btn btn-danger"></h:commandButton>

<rich:spacer width="20px;"></rich:spacer>

<h:outputLink
						value="/doc/ExciseUp/Excel/#{treasuryScrollUpdateReportAction.reportName}"
						target="_blank">
						<h:outputText value="DownloadExcel" 
							rendered="#{treasuryScrollUpdateReportAction.renderReport }" />

					</h:outputLink>


</div>
</div>

</h:form>
</f:view>
</ui:composition>