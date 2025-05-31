package com.capstone.newspectrum.enumeration;

public enum CheckType {
    title_normal, // 정상 표현
    title_hidden_1, // 의문 유발(부호) : 손흥민 우승의 비결은 바로 이것!
    title_hidden_2, // 의문 유발(은닉)
    title_sensation, // 선정표현 : 충격 고백! 톱스타 A, 10년간 숨겨온 비밀 연애 전격 공개
    title_slang, // 속어/줄임말 사용 : 한화 T1 상대로 승리! 지렸다!
    title_over_representation, // 과대 표현 : 한국 로또가 숨긴 당첨의 비밀, 로또 업계에서 비밀 밝혀져.. 업계 비상!
    title_intentional_distortion, // 주어 의도적 왜곡(주어 숨김) : 윤 대통령의 선택은? 지명 막바지로 접어들어...
    content_normal, // 정상 표현
    content_advertisement_1, // 판매정보 노출(상품 판매정보) : 안철수 후보가 윤석열 후보로부터 후보 단일화와 관련해서 어떤 제안도 듣지 못했다고 말했다. 안 후보의 유세지 정읍시는 소통 채널 활성화를 위한 카카오톡 이모티콘 우물판다를 출시했다.
    content_advertisement_2, // 판매정보 노출(부동산 판매정보)
    content_advertisement_3, // 판매정보 노출(서비스 판매정보)
    content_intentional_distortion; //의도적 상황 왜곡
}
