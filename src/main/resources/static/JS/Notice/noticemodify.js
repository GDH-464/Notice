$(document).ready(()=>{
    $(".announcement").on("change",()=>{
        if($(".announcement").is(":checked"))
        {
            $(".grade").val(1);
        }
        else
        {
            $(".grade").val(2);
        }
    })
    $(".returnbtn").on("click",()=>{
        history.back();
    })

})

