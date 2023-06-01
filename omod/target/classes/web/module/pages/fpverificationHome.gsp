<% ui.decorateWith("appui", "standardEmrPage") %>

<%= ui.resourceLinks() %>

<div id="reportPage">

    <div class="report">
        <fieldset>
            <legend>
                <i class="icon-list-alt"></i>
                <span> NDR Export for fingerprint recapture</span>
            </legend>
            <label>Start Date</label>
            <input type="date" name="startdate" id="startdate" />
            <label>End Date</label>
            <input type="date" name="enddate" id="enddate" />
            <br>
           <input type="button" value="Start" name="start" id="start" onclick="getStart()"/>
        </fieldset>
    </div>
</div>

<script>
    var jq = jQuery;
    function getStart() {

        console.log("Started Job");
        var startdate = document.getElementById("startdate").value;
        var enddate = document.getElementById("enddate").value;

        jq.ajax({
            url: "${ ui.actionLink("fpverification", "fpverificationHome", "extractFingerprint") }",
            dataType: "json",
            data:{
                'startdate':startdate,
                'enddate':enddate,
            },
            success: function (response) {
                console.log(response);

            },
            error:function(xhr){
                // alert('error ' + errthrown + '! You han error!');
                console.log(xhr);
            }
        });
    }
</script>