package com.thepace.chunjiin.service;

import android.inputmethodservice.InputMethodService;
import android.view.View;

import com.thepace.chunjiin.R;

public class KeyboardService extends InputMethodService {

    private View inputView;
    private StringBuilder currentInput = new StringBuilder();

    @Override
    public View onCreateInputView() {
        inputView = getLayoutInflater().inflate(R.layout.activity_main, null);

        // 각 버튼에 대한 클릭 리스너 설정
        inputView.findViewById(R.id.key_ㅣ).setOnClickListener(v -> handleKeyInput("ㅣ"));
        inputView.findViewById(R.id.key_ㆍ).setOnClickListener(v -> handleKeyInput("ㆍ"));
        inputView.findViewById(R.id.key_ㅡ).setOnClickListener(v -> handleKeyInput("ㅡ"));
        inputView.findViewById(R.id.key_delete).setOnClickListener(v -> handleBackspace());
        inputView.findViewById(R.id.key_space).setOnClickListener(v -> handleKeyInput(" "));

        // 나머지 자음 버튼 처리
        inputView.findViewById(R.id.key_ㄱ).setOnClickListener(v -> handleKeyInput("ㄱ"));
        inputView.findViewById(R.id.key_ㄴ).setOnClickListener(v -> handleKeyInput("ㄴ"));
        inputView.findViewById(R.id.key_ㄷ).setOnClickListener(v -> handleKeyInput("ㄷ"));
        inputView.findViewById(R.id.key_ㄹ).setOnClickListener(v -> handleKeyInput("ㄹ"));

        return inputView;
    }

    // 입력 처리 함수 (자음/모음 입력 시 호출됨)
    private void handleKeyInput(String text) {
        currentInput.append(text);  // 자음이나 모음을 StringBuilder에 추가

        // 한글 조합이 완료되었는지 확인
        if (isCompleteSyllable(currentInput.toString())) {
            // 완성된 한글을 현재 입력 필드에 전달
            getCurrentInputConnection().commitText(completeSyllable(currentInput.toString()), 1);
            currentInput.setLength(0); // 입력된 값 초기화
        }
    }

    // 자음/모음 조합이 완료되었는지 확인하는 함수
    private boolean isCompleteSyllable(String input) {
        // 간단한 예시: 최소 2글자 이상일 때 글자 조합이 완료되었다고 가정
        return input.length() >= 2;
    }

    // 완성된 한글을 반환하는 함수
    private String completeSyllable(String input) {
        // 입력된 자음과 모음을 조합하여 완성된 한글을 반환 (실제 조합 로직은 더 복잡할 수 있음)
        return input;  // 간단한 예시
    }

    // 백스페이스 처리 함수
    private void handleBackspace() {
        if (currentInput.length() > 0) {
            // 현재 조합 중인 글자가 있으면 삭제
            currentInput.deleteCharAt(currentInput.length() - 1);
        } else {
            // 이미 완성된 글자가 있으면 InputConnection에서 삭제
            getCurrentInputConnection().deleteSurroundingText(1, 0);
        }
    }

}
