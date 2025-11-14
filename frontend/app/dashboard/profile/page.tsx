'use client';

import { useState } from 'react';
import { useUserProfile, useUpdateProfile, useChangePassword } from '@/hooks/useUser';
import { useAuthStore } from '@/store/auth';

export default function ProfilePage() {
  const { user: authUser } = useAuthStore();
  const { data: profile, isLoading } = useUserProfile();
  const updateProfile = useUpdateProfile();
  const changePassword = useChangePassword();

  const [isEditingProfile, setIsEditingProfile] = useState(false);
  const [isChangingPassword, setIsChangingPassword] = useState(false);

  // Profile form state
  const [profileForm, setProfileForm] = useState({
    email: '',
    fullName: '',
    phoneNumber: '',
  });

  // Password form state
  const [passwordForm, setPasswordForm] = useState({
    oldPassword: '',
    newPassword: '',
    confirmPassword: '',
  });

  const [passwordError, setPasswordError] = useState('');

  // Initialize profile form when data loads
  useState(() => {
    if (profile) {
      setProfileForm({
        email: profile.email || '',
        fullName: profile.fullName || '',
        phoneNumber: profile.phoneNumber || '',
      });
    }
  });

  const handleEditProfile = () => {
    if (profile) {
      setProfileForm({
        email: profile.email || '',
        fullName: profile.fullName || '',
        phoneNumber: profile.phoneNumber || '',
      });
      setIsEditingProfile(true);
    }
  };

  const handleCancelEdit = () => {
    setIsEditingProfile(false);
    if (profile) {
      setProfileForm({
        email: profile.email || '',
        fullName: profile.fullName || '',
        phoneNumber: profile.phoneNumber || '',
      });
    }
  };

  const handleUpdateProfile = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await updateProfile.mutateAsync(profileForm);
      setIsEditingProfile(false);
    } catch (error) {
      console.error('Failed to update profile:', error);
    }
  };

  const handleChangePassword = async (e: React.FormEvent) => {
    e.preventDefault();
    setPasswordError('');

    if (passwordForm.newPassword !== passwordForm.confirmPassword) {
      setPasswordError('Mật khẩu xác nhận không khớp');
      return;
    }

    if (passwordForm.newPassword.length < 6) {
      setPasswordError('Mật khẩu mới phải có ít nhất 6 ký tự');
      return;
    }

    try {
      await changePassword.mutateAsync({
        oldPassword: passwordForm.oldPassword,
        newPassword: passwordForm.newPassword,
      });
      setPasswordForm({
        oldPassword: '',
        newPassword: '',
        confirmPassword: '',
      });
      setIsChangingPassword(false);
    } catch (error: any) {
      setPasswordError(error.response?.data?.message || 'Đổi mật khẩu thất bại');
    }
  };

  if (isLoading) {
    return (
      <div className="flex items-center justify-center h-64">
        <div className="inline-block animate-spin rounded-full h-8 w-8 border-b-2 border-blue-600"></div>
      </div>
    );
  }

  if (!profile) {
    return (
      <div className="text-center py-12">
        <p className="text-gray-600">Không thể tải thông tin hồ sơ</p>
      </div>
    );
  }

  return (
    <div className="max-w-4xl mx-auto space-y-6">
      <div>
        <h1 className="text-3xl font-bold text-gray-900">Hồ sơ cá nhân</h1>
        <p className="text-gray-600 mt-1">Quản lý thông tin tài khoản của bạn</p>
      </div>

      {/* Profile Information Card */}
      <div className="bg-white rounded-lg shadow">
        <div className="px-6 py-4 border-b flex justify-between items-center">
          <h2 className="text-xl font-semibold">Thông tin cá nhân</h2>
          {!isEditingProfile && (
            <button
              onClick={handleEditProfile}
              className="px-4 py-2 text-blue-600 hover:bg-blue-50 rounded-lg transition-colors"
            >
              Chỉnh sửa
            </button>
          )}
        </div>

        <div className="p-6">
          {!isEditingProfile ? (
            <div className="space-y-4">
              <div className="flex items-center space-x-4">
                <div className="w-20 h-20 bg-gradient-to-br from-blue-500 to-purple-600 rounded-full flex items-center justify-center text-white text-2xl font-bold">
                  {profile.username.charAt(0).toUpperCase()}
                </div>
                <div>
                  <h3 className="text-xl font-semibold text-gray-900">{profile.fullName || profile.username}</h3>
                  <p className="text-gray-600">@{profile.username}</p>
                </div>
              </div>

              <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mt-6">
                <div>
                  <label className="text-sm font-medium text-gray-500">Email</label>
                  <p className="mt-1 text-gray-900">{profile.email || 'Chưa cập nhật'}</p>
                </div>
                <div>
                  <label className="text-sm font-medium text-gray-500">Số điện thoại</label>
                  <p className="mt-1 text-gray-900">{profile.phoneNumber || 'Chưa cập nhật'}</p>
                </div>
                <div>
                  <label className="text-sm font-medium text-gray-500">Tên đầy đủ</label>
                  <p className="mt-1 text-gray-900">{profile.fullName || 'Chưa cập nhật'}</p>
                </div>
                <div>
                  <label className="text-sm font-medium text-gray-500">Vai trò</label>
                  <p className="mt-1">
                    {profile.roles.map((role, index) => (
                      <span
                        key={index}
                        className="inline-block px-2 py-1 text-xs font-semibold rounded-full bg-blue-100 text-blue-800 mr-2"
                      >
                        {role}
                      </span>
                    ))}
                  </p>
                </div>
              </div>
            </div>
          ) : (
            <form onSubmit={handleUpdateProfile} className="space-y-4">
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">
                    Email
                  </label>
                  <input
                    type="email"
                    value={profileForm.email}
                    onChange={(e) => setProfileForm({ ...profileForm, email: e.target.value })}
                    className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">
                    Số điện thoại
                  </label>
                  <input
                    type="tel"
                    value={profileForm.phoneNumber}
                    onChange={(e) => setProfileForm({ ...profileForm, phoneNumber: e.target.value })}
                    className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
                <div className="md:col-span-2">
                  <label className="block text-sm font-medium text-gray-700 mb-1">
                    Tên đầy đủ
                  </label>
                  <input
                    type="text"
                    value={profileForm.fullName}
                    onChange={(e) => setProfileForm({ ...profileForm, fullName: e.target.value })}
                    className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
              </div>

              {updateProfile.isError && (
                <div className="p-4 bg-red-50 text-red-700 rounded-lg">
                  Cập nhật thất bại. Vui lòng thử lại.
                </div>
              )}

              {updateProfile.isSuccess && (
                <div className="p-4 bg-green-50 text-green-700 rounded-lg">
                  Cập nhật thành công!
                </div>
              )}

              <div className="flex justify-end space-x-3">
                <button
                  type="button"
                  onClick={handleCancelEdit}
                  className="px-4 py-2 text-gray-700 hover:bg-gray-100 rounded-lg"
                >
                  Hủy
                </button>
                <button
                  type="submit"
                  disabled={updateProfile.isPending}
                  className="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 disabled:opacity-50 disabled:cursor-not-allowed"
                >
                  {updateProfile.isPending ? 'Đang lưu...' : 'Lưu thay đổi'}
                </button>
              </div>
            </form>
          )}
        </div>
      </div>

      {/* Change Password Card */}
      <div className="bg-white rounded-lg shadow">
        <div className="px-6 py-4 border-b flex justify-between items-center">
          <h2 className="text-xl font-semibold">Đổi mật khẩu</h2>
          {!isChangingPassword && (
            <button
              onClick={() => setIsChangingPassword(true)}
              className="px-4 py-2 text-blue-600 hover:bg-blue-50 rounded-lg transition-colors"
            >
              Đổi mật khẩu
            </button>
          )}
        </div>

        {isChangingPassword && (
          <div className="p-6">
            <form onSubmit={handleChangePassword} className="space-y-4">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  Mật khẩu hiện tại
                </label>
                <input
                  type="password"
                  value={passwordForm.oldPassword}
                  onChange={(e) => setPasswordForm({ ...passwordForm, oldPassword: e.target.value })}
                  className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  required
                />
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  Mật khẩu mới
                </label>
                <input
                  type="password"
                  value={passwordForm.newPassword}
                  onChange={(e) => setPasswordForm({ ...passwordForm, newPassword: e.target.value })}
                  className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  required
                />
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  Xác nhận mật khẩu mới
                </label>
                <input
                  type="password"
                  value={passwordForm.confirmPassword}
                  onChange={(e) => setPasswordForm({ ...passwordForm, confirmPassword: e.target.value })}
                  className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  required
                />
              </div>

              {passwordError && (
                <div className="p-4 bg-red-50 text-red-700 rounded-lg">
                  {passwordError}
                </div>
              )}

              {changePassword.isSuccess && (
                <div className="p-4 bg-green-50 text-green-700 rounded-lg">
                  Đổi mật khẩu thành công!
                </div>
              )}

              <div className="flex justify-end space-x-3">
                <button
                  type="button"
                  onClick={() => {
                    setIsChangingPassword(false);
                    setPasswordForm({
                      oldPassword: '',
                      newPassword: '',
                      confirmPassword: '',
                    });
                    setPasswordError('');
                  }}
                  className="px-4 py-2 text-gray-700 hover:bg-gray-100 rounded-lg"
                >
                  Hủy
                </button>
                <button
                  type="submit"
                  disabled={changePassword.isPending}
                  className="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 disabled:opacity-50 disabled:cursor-not-allowed"
                >
                  {changePassword.isPending ? 'Đang lưu...' : 'Đổi mật khẩu'}
                </button>
              </div>
            </form>
          </div>
        )}
      </div>

      {/* Account Info Card */}
      <div className="bg-white rounded-lg shadow p-6">
        <h2 className="text-xl font-semibold mb-4">Thông tin tài khoản</h2>
        <div className="space-y-3 text-sm text-gray-600">
          <div className="flex justify-between">
            <span>Tên đăng nhập:</span>
            <span className="font-medium text-gray-900">{profile.username}</span>
          </div>
          <div className="flex justify-between">
            <span>ID người dùng:</span>
            <span className="font-medium text-gray-900">{profile.id}</span>
          </div>
          <div className="flex justify-between">
            <span>Trạng thái:</span>
            <span className="inline-flex items-center px-2 py-1 rounded-full text-xs font-semibold bg-green-100 text-green-800">
              <span className="w-2 h-2 bg-green-500 rounded-full mr-1"></span>
              Hoạt động
            </span>
          </div>
        </div>
      </div>
    </div>
  );
}
