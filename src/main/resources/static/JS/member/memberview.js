$(document).ready(()=>{
    $(".returnbtn").on("click",()=>{
             history.back();
         })
    $(".deletebtn").on("click",(event)=>{
    event.preventDefault();
      if(confirm("정말로 삭제하시겠습니까?"))
      {
        window.location.href = $(event.currentTarget).attr("href")
      }
    })
    $(".deletebtn2").on("click",(event)=>{
    event.preventDefault();
      if(confirm("정말로 탈퇴하시겠습니까?"))
      {
        window.location.href = $(event.currentTarget).attr("href")
      }
    })
})
