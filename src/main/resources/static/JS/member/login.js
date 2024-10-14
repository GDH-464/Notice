$(document).ready(()=>{
    //돌아가기
    $(".returnbtn").on("click",()=>{
        window.location.href = '/';
    })
    //로그인버튼을 눌렀을때
    $(".login").on("submit",(event)=>{
    event.preventDefault();
    const userid = $("#userid").val();
    const pwd = $("#pwd").val();
     if (userid == "" || pwd == "") {
            alert("아이디와 비밀번호를 입력해주세요");
            return;
     }
    //아이디 비밀번호를 비교하고 세션 생성
    $.ajax({
         url: '/login_check',
         method: 'POST',
         contentType: 'application/json',
         data: JSON.stringify({userid : userid,pwd:pwd}),
         dataType: 'json',
         success: function(data)
         {
            const str = data.result.trim();
            if(str == "N")
            {
                alert("아이디 또는 비밀번호가 일치하지않습니다");
                return;
            }
            if(str == "PN")
            {
                alert("비밀번호가 일치하지않습니다");
                return;
            }
            if (str === "Y")
            {
                window.location.href = '/'
            }
         },
         error: function(err) {
             alert('error');
         }
         });
    })
})