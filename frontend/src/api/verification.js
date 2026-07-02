const BASE_URL = import.meta.env.VITE_API_BASE_URL;

const authHeader = () => ({
  Authorization: `Bearer ${localStorage.getItem("token")}`,
});

// 조회
export const getVerifications = async () => {
  const res = await fetch(`${BASE_URL}/api/v1/verifications`, {
    headers: authHeader(),
  });

  if (!res.ok) {
    throw new Error("조회 이력 불러오기 실패");
  }

  return res.json();
};

// 저장
export async function saveProductVerification(data) {
  const res = await fetch(`${BASE_URL}/api/v1/verifications/product`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      ...authHeader(),
    },
    body: JSON.stringify(data),
  });

  if (!res.ok) throw new Error("저장 실패");
  return res.json();
}

export async function saveLotVerification(data) {
  const res = await fetch(`${BASE_URL}/api/v1/verifications/lot`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      ...authHeader(),
    },
    body: JSON.stringify(data),
  });

  if (!res.ok) throw new Error("저장 실패");
  return res.json();
}

export async function saveImageVerification(file) {
  const token = localStorage.getItem("token");

  if (!token) {
    throw new Error("토큰 없음 (로그인 필요)");
  }

  const formData = new FormData();
  formData.append("file", file);

  const res = await fetch(`${BASE_URL}/api/v1/verifications/image`, {
    method: "POST",
    headers: {
      Authorization: `Bearer ${token}`,
    },
    body: formData,
  });

  if (!res.ok) throw new Error("저장 실패");
  return res.json();
}

// 삭제
export const deleteVerification = async (id) => {
  const res = await fetch(`${BASE_URL}/api/v1/verifications/${id}`, {
    method: "DELETE",
    headers: authHeader(),
  });

  if (!res.ok) {
    throw new Error("조회 이력 삭제 실패");
  }
};
