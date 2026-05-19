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
export const saveVerification = async (payload) => {
  const res = await fetch(`${BASE_URL}/api/v1/verifications`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      ...authHeader(),
    },
    body: JSON.stringify(payload),
  });

  if (!res.ok) {
    throw new Error("조회 이력 저장 실패");
  }

  return res.json();
};

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
