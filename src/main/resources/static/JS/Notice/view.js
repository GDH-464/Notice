$(document).ready(()=>{
    $(".returnbtn").on("click",()=>{
            history.back();
    })

    $(".commentaddbtn").on("click", () => {
      console.log($(".noticeidx").val());
        $.ajax({
          url: '/commentadd',
          type: 'POST',
          contentType: 'application/json',
          data: JSON.stringify({
            userid: $(".userid").val(),
            nick: $(".nick").val(),
            comment: $(".commentadd").val(),
            noticeidx: $(".noticeidx").val()
          }),
          success: () => {},
          error: () => {
            alert('오류가 발생했습니다');
          }
        });
      });
})