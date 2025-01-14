USE [master]
GO
/****** Object:  Database [Nxtwav]    Script Date: 1/24/2021 2:21:35 PM ******/
CREATE DATABASE [Nxtwav]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'irgs_user_roles', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL11.SQLEXPRESS\MSSQL\DATA\irgs_user_roles.mdf' , SIZE = 4096KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'irgs_user_roles_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL11.SQLEXPRESS\MSSQL\DATA\irgs_user_roles_log.ldf' , SIZE = 1024KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [Nxtwav] SET COMPATIBILITY_LEVEL = 110
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [Nxtwav].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [Nxtwav] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [Nxtwav] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [Nxtwav] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [Nxtwav] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [Nxtwav] SET ARITHABORT OFF 
GO
ALTER DATABASE [Nxtwav] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [Nxtwav] SET AUTO_CREATE_STATISTICS ON 
GO
ALTER DATABASE [Nxtwav] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [Nxtwav] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [Nxtwav] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [Nxtwav] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [Nxtwav] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [Nxtwav] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [Nxtwav] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [Nxtwav] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [Nxtwav] SET  DISABLE_BROKER 
GO
ALTER DATABASE [Nxtwav] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [Nxtwav] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [Nxtwav] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [Nxtwav] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [Nxtwav] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [Nxtwav] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [Nxtwav] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [Nxtwav] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [Nxtwav] SET  MULTI_USER 
GO
ALTER DATABASE [Nxtwav] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [Nxtwav] SET DB_CHAINING OFF 
GO
ALTER DATABASE [Nxtwav] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [Nxtwav] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
USE [Nxtwav]
GO
/****** Object:  Table [dbo].[accesspolicy_domain]    Script Date: 1/24/2021 2:21:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[accesspolicy_domain](
	[accesspolicy_id] [bigint] IDENTITY(1,1) NOT NULL,
	[accesspolicy_name] [varchar](255) NULL,
	[created_by] [bigint] NOT NULL,
	[created_on] [datetime2](7) NULL,
	[delete_permission] [bit] NOT NULL,
	[read_permission] [bit] NOT NULL,
	[update_permission] [bit] NOT NULL,
	[updated_by] [bigint] NOT NULL,
	[updated_on] [datetime2](7) NULL,
	[upload_permission] [bit] NOT NULL,
	[write_permission] [bit] NOT NULL,
	[feature_id] [bigint] NULL,
	[module_id] [bigint] NULL,
	[sub_feature_id] [bigint] NULL,
	[sub_module_id] [bigint] NULL,
	[web_role_id] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[accesspolicy_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[application_access]    Script Date: 1/24/2021 2:21:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[application_access](
	[application_access_id] [bigint] IDENTITY(1,1) NOT NULL,
	[application_access_name] [varchar](255) NOT NULL,
	[created_by] [bigint] NOT NULL,
	[created_on] [datetime2](7) NULL,
	[updated_by] [bigint] NOT NULL,
	[updated_on] [datetime2](7) NULL,
PRIMARY KEY CLUSTERED 
(
	[application_access_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[asset]    Script Date: 1/24/2021 2:21:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[asset](
	[asset_id] [bigint] IDENTITY(1,1) NOT NULL,
	[asset_code] [varchar](255) NULL,
	[asset_name] [varchar](255) NULL,
	[created_by] [bigint] NOT NULL,
	[created_on] [datetime2](7) NULL,
	[far_no] [varchar](255) NULL,
	[item_no] [bigint] NULL,
	[updated_by] [bigint] NOT NULL,
	[updated_on] [datetime2](7) NULL,
	[equipment_id] [bigint] NULL,
	[model_id] [bigint] NOT NULL,
	[web_master_id] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[asset_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[brand]    Script Date: 1/24/2021 2:21:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[brand](
	[brand_id] [bigint] IDENTITY(1,1) NOT NULL,
	[brand_code] [varchar](255) NOT NULL,
	[brand_name] [varchar](255) NULL,
	[created_by] [bigint] NOT NULL,
	[created_on] [datetime2](7) NULL,
	[updated_by] [bigint] NOT NULL,
	[updated_on] [datetime2](7) NULL,
PRIMARY KEY CLUSTERED 
(
	[brand_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[city]    Script Date: 1/24/2021 2:21:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[city](
	[city_id] [bigint] IDENTITY(1,1) NOT NULL,
	[city_name] [varchar](255) NOT NULL,
	[created_by] [bigint] NOT NULL,
	[created_date] [datetime2](7) NULL,
	[update_date] [datetime2](7) NULL,
	[updated_by] [bigint] NOT NULL,
	[state_id] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[city_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[cluster]    Script Date: 1/24/2021 2:21:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cluster](
	[cluster_id] [bigint] IDENTITY(1,1) NOT NULL,
	[cluster_name] [varchar](255) NULL,
	[created_by] [bigint] NOT NULL,
	[created_date] [datetime2](7) NULL,
	[update_date] [datetime2](7) NULL,
	[updated_by] [bigint] NOT NULL,
	[web_app_id] [bigint] NULL,
	[city_id] [bigint] NULL,
	[country_id] [bigint] NULL,
	[region_id] [bigint] NULL,
	[state_id] [bigint] NULL,
	[web_role_id] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[cluster_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[cluster_user]    Script Date: 1/24/2021 2:21:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cluster_user](
	[cluster_user_id] [bigint] IDENTITY(1,1) NOT NULL,
	[created_by] [bigint] NOT NULL,
	[created_date] [datetime2](7) NULL,
	[update_date] [datetime2](7) NULL,
	[updated_by] [bigint] NOT NULL,
	[user_id] [bigint] NULL,
	[user_name] [varchar](255) NULL,
	[cluster_id] [bigint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[cluster_user_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[country]    Script Date: 1/24/2021 2:21:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[country](
	[country_id] [bigint] IDENTITY(1,1) NOT NULL,
	[country_name] [varchar](255) NOT NULL,
	[created_by] [bigint] NOT NULL,
	[created_date] [datetime2](7) NULL,
	[update_date] [datetime2](7) NULL,
	[updated_by] [bigint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[country_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[engineer]    Script Date: 1/24/2021 2:21:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[engineer](
	[engineer_id] [bigint] IDENTITY(1,1) NOT NULL,
	[created_by] [bigint] NOT NULL,
	[created_on] [datetime2](7) NULL,
	[email_id] [varchar](255) NULL,
	[engineer_name] [varchar](255) NULL,
	[mobile_no] [varchar](255) NOT NULL,
	[updated_by] [bigint] NOT NULL,
	[updated_on] [datetime2](7) NULL,
	[vendor_id] [bigint] NOT NULL,
	[vendor_name] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[engineer_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[equipment]    Script Date: 1/24/2021 2:21:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[equipment](
	[equipment_id] [bigint] IDENTITY(1,1) NOT NULL,
	[created_by] [bigint] NOT NULL,
	[created_on] [datetime2](7) NULL,
	[equipment_code] [varchar](255) NOT NULL,
	[equipment_cost] [float] NULL,
	[equipment_name] [varchar](255) NOT NULL,
	[updated_by] [bigint] NOT NULL,
	[updated_on] [datetime2](7) NULL,
	[equipment_type_id] [bigint] NOT NULL,
	[vendor_id] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[equipment_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[equipment_type]    Script Date: 1/24/2021 2:21:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[equipment_type](
	[equipment_type_id] [bigint] IDENTITY(1,1) NOT NULL,
	[created_by] [bigint] NOT NULL,
	[created_on] [datetime2](7) NULL,
	[equipment_type_name] [varchar](255) NOT NULL,
	[updated_by] [bigint] NOT NULL,
	[updated_on] [datetime2](7) NULL,
PRIMARY KEY CLUSTERED 
(
	[equipment_type_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[feature]    Script Date: 1/24/2021 2:21:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[feature](
	[feature_id] [bigint] IDENTITY(1,1) NOT NULL,
	[created_by] [bigint] NOT NULL,
	[created_on] [datetime2](7) NULL,
	[enabled_status] [bit] NOT NULL,
	[feature_name] [varchar](255) NOT NULL,
	[featureurl] [varchar](255) NULL,
	[icon_name] [varchar](255) NULL,
	[updated_by] [bigint] NOT NULL,
	[updated_on] [datetime2](7) NULL,
	[sub_module_id] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[feature_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[futures]    Script Date: 1/24/2021 2:21:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[futures](
	[future_id] [bigint] IDENTITY(1,1) NOT NULL,
	[created_by] [bigint] NOT NULL,
	[created_on] [datetime2](7) NULL,
	[enabled_status] [bit] NOT NULL,
	[future_name] [varchar](255) NOT NULL,
	[futureurl] [varchar](255) NULL,
	[icon_name] [varchar](255) NULL,
	[updated_by] [bigint] NOT NULL,
	[updated_on] [datetime2](7) NULL,
	[sub_model_id] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[future_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[inventory]    Script Date: 1/24/2021 2:21:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[inventory](
	[inventory_id] [bigint] IDENTITY(1,1) NOT NULL,
	[created_by] [bigint] NOT NULL,
	[created_on] [datetime2](7) NULL,
	[description] [varchar](255) NULL,
	[er_no] [varchar](255) NULL,
	[installation_date] [datetime2](7) NULL,
	[manufacture_date] [datetime2](7) NULL,
	[quantity] [bigint] NULL,
	[updated_by] [bigint] NOT NULL,
	[updated_on] [datetime2](7) NULL,
	[asset_id] [bigint] NOT NULL,
	[store_id] [bigint] NOT NULL,
	[vendor_id] [bigint] NULL,
	[web_master_id] [bigint] NOT NULL,
	[far_no] [varchar](255) NULL,
 CONSTRAINT [PK__inventor__B59ACC49C3E19B39] PRIMARY KEY CLUSTERED 
(
	[inventory_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[model]    Script Date: 1/24/2021 2:21:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[model](
	[model_id] [bigint] IDENTITY(1,1) NOT NULL,
	[created_by] [bigint] NOT NULL,
	[created_on] [datetime2](7) NULL,
	[enabled_status] [bit] NULL,
	[icon_name] [varchar](255) NULL,
	[model_name] [varchar](255) NOT NULL,
	[modelurl] [varchar](255) NULL,
	[updated_by] [bigint] NOT NULL,
	[updated_on] [datetime2](7) NULL,
	[model_no] [varchar](255) NOT NULL,
	[brand_id] [bigint] NOT NULL,
 CONSTRAINT [PK__model__DC39CAF4F5936C2C] PRIMARY KEY CLUSTERED 
(
	[model_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[module]    Script Date: 1/24/2021 2:21:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[module](
	[model_id] [bigint] IDENTITY(1,1) NOT NULL,
	[created_by] [bigint] NOT NULL,
	[created_on] [datetime2](7) NULL,
	[enabled_status] [bit] NOT NULL,
	[icon_name] [varchar](255) NULL,
	[model_name] [varchar](255) NOT NULL,
	[modelurl] [varchar](255) NULL,
	[updated_by] [bigint] NOT NULL,
	[updated_on] [datetime2](7) NULL,
	[module_id] [bigint] NOT NULL,
	[module_name] [varchar](255) NOT NULL,
	[moduleurl] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[model_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[opertion_type]    Script Date: 1/24/2021 2:21:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[opertion_type](
	[opertion_type_id] [bigint] IDENTITY(1,1) NOT NULL,
	[opertion_type] [varchar](255) NULL,
	[web_role_id] [bigint] NULL,
 CONSTRAINT [PK__opertion__78792C88852D02DD] PRIMARY KEY CLUSTERED 
(
	[opertion_type_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[owner_type]    Script Date: 1/24/2021 2:21:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[owner_type](
	[owner_type_id] [bigint] IDENTITY(1,1) NOT NULL,
	[created_by] [bigint] NOT NULL,
	[created_on] [datetime2](7) NULL,
	[owner_type_name] [varchar](255) NULL,
	[updated_by] [bigint] NOT NULL,
	[updated_on] [datetime2](7) NULL,
PRIMARY KEY CLUSTERED 
(
	[owner_type_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[region]    Script Date: 1/24/2021 2:21:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[region](
	[region_id] [bigint] IDENTITY(1,1) NOT NULL,
	[created_by] [bigint] NOT NULL,
	[created_date] [datetime2](7) NULL,
	[region_name] [varchar](255) NOT NULL,
	[update_date] [datetime2](7) NULL,
	[updated_by] [bigint] NOT NULL,
	[city_id] [bigint] NULL,
	[state_id] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[region_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[role]    Script Date: 1/24/2021 2:21:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[role](
	[role_id] [bigint] IDENTITY(1,1) NOT NULL,
	[created_by] [bigint] NOT NULL,
	[created_on] [datetime2](7) NULL,
	[enabled_status] [bit] NOT NULL,
	[role_name] [varchar](255) NOT NULL,
	[updated_by] [bigint] NOT NULL,
	[updated_on] [datetime2](7) NULL,
	[access_master_id] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[role_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[state]    Script Date: 1/24/2021 2:21:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[state](
	[state_id] [bigint] IDENTITY(1,1) NOT NULL,
	[created_by] [bigint] NOT NULL,
	[created_date] [datetime2](7) NULL,
	[state_name] [varchar](255) NOT NULL,
	[update_date] [datetime2](7) NULL,
	[updated_by] [bigint] NOT NULL,
	[country_id] [bigint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[state_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[store]    Script Date: 1/24/2021 2:21:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[store](
	[store_id] [bigint] IDENTITY(1,1) NOT NULL,
	[address] [varchar](255) NULL,
	[address1] [varchar](255) NULL,
	[address2] [varchar](255) NULL,
	[costcentre] [varchar](255) NOT NULL,
	[created_by] [bigint] NOT NULL,
	[created_on] [datetime2](7) NULL,
	[email_id] [varchar](255) NULL,
	[fax1] [varchar](255) NULL,
	[fax2] [varchar](255) NULL,
	[geo_location] [varchar](255) NULL,
	[geolocation_name] [varchar](255) NULL,
	[latitude] [varchar](255) NULL,
	[longitude] [varchar](255) NULL,
	[opto_alloted] [varchar](255) NULL,
	[owner_name] [varchar](255) NULL,
	[phone_no] [varchar](255) NULL,
	[phone_no2] [varchar](255) NULL,
	[pin_code] [varchar](255) NULL,
	[reporting_to] [varchar](255) NULL,
	[star_flag] [bit] NOT NULL,
	[store_code] [varchar](255) NOT NULL,
	[store_flag] [bit] NOT NULL,
	[store_locality] [varchar](255) NULL,
	[store_name] [varchar](255) NULL,
	[store_status] [varchar](255) NULL,
	[updated_by] [bigint] NOT NULL,
	[updated_on] [datetime2](7) NULL,
	[city_id] [bigint] NULL,
	[country_id] [bigint] NULL,
	[owner_type_id] [bigint] NULL,
	[region_id] [bigint] NULL,
	[state_id] [bigint] NULL,
	[store_business_service_type_id] [bigint] NULL,
	[store_service_type_id] [bigint] NULL,
	[web_master_id] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[store_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[store_alloted]    Script Date: 1/24/2021 2:21:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[store_alloted](
	[store_alloted_id] [bigint] IDENTITY(1,1) NOT NULL,
	[created_by] [bigint] NOT NULL,
	[created_on] [datetime2](7) NULL,
	[store_alloted_type] [varchar](255) NULL,
	[updated_by] [bigint] NOT NULL,
	[updated_on] [datetime2](7) NULL,
PRIMARY KEY CLUSTERED 
(
	[store_alloted_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[store_alloted_details]    Script Date: 1/24/2021 2:21:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[store_alloted_details](
	[store_alloted_details_id] [bigint] IDENTITY(1,1) NOT NULL,
	[created_by] [bigint] NOT NULL,
	[created_on] [datetime2](7) NULL,
	[updated_by] [bigint] NOT NULL,
	[updated_on] [datetime2](7) NULL,
	[store_id] [bigint] NULL,
	[store_alloted_id] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[store_alloted_details_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[store_business_service_type]    Script Date: 1/24/2021 2:21:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[store_business_service_type](
	[store_business_service_type_id] [bigint] IDENTITY(1,1) NOT NULL,
	[created_by] [bigint] NOT NULL,
	[created_on] [datetime2](7) NULL,
	[store_business_service_type_name] [varchar](255) NULL,
	[updated_by] [bigint] NOT NULL,
	[updated_on] [datetime2](7) NULL,
PRIMARY KEY CLUSTERED 
(
	[store_business_service_type_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[store_service_type]    Script Date: 1/24/2021 2:21:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[store_service_type](
	[store_service_type_id] [bigint] IDENTITY(1,1) NOT NULL,
	[created_by] [bigint] NOT NULL,
	[created_on] [datetime2](7) NULL,
	[sla] [varchar](255) NULL,
	[store_service_type_name] [varchar](255) NULL,
	[updated_by] [bigint] NOT NULL,
	[updated_on] [datetime2](7) NULL,
PRIMARY KEY CLUSTERED 
(
	[store_service_type_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[sub_features]    Script Date: 1/24/2021 2:21:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[sub_features](
	[sub_feature_id] [bigint] IDENTITY(1,1) NOT NULL,
	[created_by] [bigint] NOT NULL,
	[created_on] [datetime2](7) NULL,
	[enabled_status] [bit] NOT NULL,
	[icon_name] [varchar](255) NULL,
	[sub_feature_name] [varchar](255) NOT NULL,
	[sub_featureurl] [varchar](255) NULL,
	[updated_by] [bigint] NOT NULL,
	[updated_on] [datetime2](7) NULL,
	[feature_id] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[sub_feature_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[sub_futures]    Script Date: 1/24/2021 2:21:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[sub_futures](
	[sub_future_id] [bigint] IDENTITY(1,1) NOT NULL,
	[created_by] [bigint] NOT NULL,
	[created_on] [datetime2](7) NULL,
	[enabled_status] [bit] NOT NULL,
	[icon_name] [varchar](255) NULL,
	[sub_future_name] [varchar](255) NOT NULL,
	[sub_futureurl] [varchar](255) NULL,
	[updated_by] [bigint] NOT NULL,
	[updated_on] [datetime2](7) NULL,
	[future_id] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[sub_future_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[sub_model]    Script Date: 1/24/2021 2:21:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[sub_model](
	[sub_model_id] [bigint] IDENTITY(1,1) NOT NULL,
	[created_by] [bigint] NOT NULL,
	[created_on] [datetime2](7) NULL,
	[enabled_status] [bit] NOT NULL,
	[icon_name] [varchar](255) NULL,
	[sub_model_name] [varchar](255) NOT NULL,
	[sub_model_url] [varchar](255) NULL,
	[updated_by] [bigint] NOT NULL,
	[updated_on] [datetime2](7) NULL,
	[model_id] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[sub_model_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[sub_module]    Script Date: 1/24/2021 2:21:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[sub_module](
	[sub_model_id] [bigint] IDENTITY(1,1) NOT NULL,
	[created_by] [bigint] NOT NULL,
	[created_on] [datetime2](7) NULL,
	[enabled_status] [bit] NOT NULL,
	[icon_name] [varchar](255) NULL,
	[sub_model_name] [varchar](255) NOT NULL,
	[sub_model_url] [varchar](255) NULL,
	[updated_by] [bigint] NOT NULL,
	[updated_on] [datetime2](7) NULL,
	[model_id] [bigint] NULL,
	[sub_module_id] [bigint] NOT NULL,
	[sub_module_name] [varchar](255) NOT NULL,
	[sub_module_url] [varchar](255) NULL,
	[module_id] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[sub_model_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[uploaded_document]    Script Date: 1/24/2021 2:21:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[uploaded_document](
	[uploaded_document_id] [bigint] IDENTITY(1,1) NOT NULL,
	[content_type] [varchar](255) NULL,
	[description] [varchar](255) NULL,
	[file_upload_type] [int] NULL,
	[filename] [varchar](255) NULL,
	[name] [varchar](255) NULL,
	[processed] [bit] NOT NULL,
	[role_id] [bigint] NULL,
	[uploaded_date] [date] NULL,
	[time_stamp] [datetime2](7) NULL,
	[uploaded_user_id] [bigint] NULL,
	[uploaded_user_name] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[uploaded_document_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[users]    Script Date: 1/24/2021 2:21:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[users](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[created_by] [bigint] NULL,
	[created_on] [datetime2](7) NULL,
	[modified_by] [varchar](255) NULL,
	[modified_on] [datetime2](7) NULL,
	[account_non_expired] [bit] NULL,
	[account_non_locked] [bit] NULL,
	[credentials_non_expired] [bit] NULL,
	[is_enabled] [bit] NULL,
	[last_login] [datetime2](7) NULL,
	[login_count] [bigint] NULL,
	[password] [varchar](255) NOT NULL,
	[username] [varchar](255) NOT NULL,
	[web_role_id] [bigint] NULL,
	[address] [varchar](255) NULL,
	[discription] [varchar](255) NULL,
	[email] [varchar](255) NULL,
	[phone_no] [varchar](255) NULL,
	[first_name] [varchar](255) NULL,
	[last_name] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[vendor]    Script Date: 1/24/2021 2:21:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[vendor](
	[vendor_id] [bigint] IDENTITY(1,1) NOT NULL,
	[billing_address] [varchar](255) NULL,
	[billing_email_id] [varchar](255) NULL,
	[contact_number] [varchar](255) NULL,
	[contact_person] [varchar](255) NULL,
	[created_by] [bigint] NOT NULL,
	[created_on] [datetime2](7) NULL,
	[pin_code] [varchar](255) NULL,
	[service_address1] [varchar](255) NULL,
	[service_address2] [varchar](255) NULL,
	[service_email_id1] [varchar](255) NULL,
	[service_email_id2] [varchar](255) NULL,
	[updated_by] [bigint] NOT NULL,
	[updated_on] [datetime2](7) NULL,
	[vendor_code] [varchar](255) NOT NULL,
	[vendor_name] [varchar](255) NULL,
	[vendor_status] [varchar](255) NULL,
	[city_id] [bigint] NULL,
	[vendor_type_id] [bigint] NOT NULL,
	[web_master_id] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[vendor_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[vendor_equipment]    Script Date: 1/24/2021 2:21:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[vendor_equipment](
	[vendor_equipment_id] [bigint] IDENTITY(1,1) NOT NULL,
	[created_by] [bigint] NOT NULL,
	[created_on] [datetime2](7) NULL,
	[equipment_id] [bigint] NULL,
	[updated_by] [bigint] NOT NULL,
	[updated_on] [datetime2](7) NULL,
	[vendor_id] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[vendor_equipment_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[vendor_type]    Script Date: 1/24/2021 2:21:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[vendor_type](
	[vendor_type_id] [bigint] IDENTITY(1,1) NOT NULL,
	[created_by] [bigint] NOT NULL,
	[created_on] [datetime2](7) NULL,
	[updated_by] [bigint] NOT NULL,
	[updated_on] [datetime2](7) NULL,
	[vendor_type_name] [varchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[vendor_type_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[web_master]    Script Date: 1/24/2021 2:21:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[web_master](
	[web_master_id] [bigint] IDENTITY(1,1) NOT NULL,
	[created_by] [bigint] NOT NULL,
	[created_on] [datetime2](7) NULL,
	[enabled_status] [bit] NOT NULL,
	[updated_by] [bigint] NOT NULL,
	[updated_on] [datetime2](7) NULL,
	[web_master_name] [varchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[web_master_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[web_role]    Script Date: 1/24/2021 2:21:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[web_role](
	[web_role_id] [bigint] IDENTITY(1,1) NOT NULL,
	[created_by] [bigint] NOT NULL,
	[created_on] [datetime2](7) NULL,
	[enabled_status] [bit] NOT NULL,
	[updated_by] [bigint] NOT NULL,
	[updated_on] [datetime2](7) NULL,
	[reporting_id] [bigint] NULL,
	[role_id] [bigint] NULL,
	[access_master_id] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[web_role_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET IDENTITY_INSERT [dbo].[application_access] ON 

INSERT [dbo].[application_access] ([application_access_id], [application_access_name], [created_by], [created_on], [updated_by], [updated_on]) VALUES (2, N'MainApplication', 1, NULL, 1, NULL)
INSERT [dbo].[application_access] ([application_access_id], [application_access_name], [created_by], [created_on], [updated_by], [updated_on]) VALUES (3, N'WebApplication', 1, NULL, 1, NULL)
SET IDENTITY_INSERT [dbo].[application_access] OFF
SET IDENTITY_INSERT [dbo].[asset] ON 

INSERT [dbo].[asset] ([asset_id], [asset_code], [asset_name], [created_by], [created_on], [far_no], [item_no], [updated_by], [updated_on], [equipment_id], [model_id], [web_master_id]) VALUES (1, N'4cc/123', N'accc', 0, CAST(0x07D0ED1D379710420B AS DateTime2), N'12345', 123, 0, NULL, 1, 1, 1)
SET IDENTITY_INSERT [dbo].[asset] OFF
SET IDENTITY_INSERT [dbo].[brand] ON 

INSERT [dbo].[brand] ([brand_id], [brand_code], [brand_name], [created_by], [created_on], [updated_by], [updated_on]) VALUES (1, N'2015', N'Kirloskar LG', 0, CAST(0x0750235D598002420B AS DateTime2), 0, NULL)
INSERT [dbo].[brand] ([brand_id], [brand_code], [brand_name], [created_by], [created_on], [updated_by], [updated_on]) VALUES (2, N'MAP50', N'ABC', 0, CAST(0x07D0E02FAD8002420B AS DateTime2), 0, CAST(0x07F00322A88202420B AS DateTime2))
INSERT [dbo].[brand] ([brand_id], [brand_code], [brand_name], [created_by], [created_on], [updated_by], [updated_on]) VALUES (3, N'DSP 250MM', N'Havells', 0, CAST(0x07E0704EE28202420B AS DateTime2), 0, NULL)
SET IDENTITY_INSERT [dbo].[brand] OFF
SET IDENTITY_INSERT [dbo].[city] ON 

INSERT [dbo].[city] ([city_id], [city_name], [created_by], [created_date], [update_date], [updated_by], [state_id]) VALUES (1, N'BENGALURU', 0, CAST(0x0730DF952B6102420B AS DateTime2), CAST(0x07D08DE0786102420B AS DateTime2), 0, NULL)
INSERT [dbo].[city] ([city_id], [city_name], [created_by], [created_date], [update_date], [updated_by], [state_id]) VALUES (2, N'KOLKATTA', 0, CAST(0x07D070CD3A6102420B AS DateTime2), CAST(0x07A0CE2C8A6102420B AS DateTime2), 0, NULL)
INSERT [dbo].[city] ([city_id], [city_name], [created_by], [created_date], [update_date], [updated_by], [state_id]) VALUES (3, N'NEW DELHI', 0, CAST(0x07D06BDC496102420B AS DateTime2), CAST(0x07505AE0906102420B AS DateTime2), 0, NULL)
INSERT [dbo].[city] ([city_id], [city_name], [created_by], [created_date], [update_date], [updated_by], [state_id]) VALUES (4, N'MYSURU', 0, CAST(0x07305224DE6103420B AS DateTime2), CAST(0x07E0B7EAD46103420B AS DateTime2), 0, NULL)
INSERT [dbo].[city] ([city_id], [city_name], [created_by], [created_date], [update_date], [updated_by], [state_id]) VALUES (5, N'GULBARGA', 0, CAST(0x07805005FA6103420B AS DateTime2), CAST(0x07C03D50E16103420B AS DateTime2), 0, NULL)
INSERT [dbo].[city] ([city_id], [city_name], [created_by], [created_date], [update_date], [updated_by], [state_id]) VALUES (6, N'HYDERABAD', 0, CAST(0x07D0C286066203420B AS DateTime2), CAST(0x0720D1EFFD6103420B AS DateTime2), 0, NULL)
INSERT [dbo].[city] ([city_id], [city_name], [created_by], [created_date], [update_date], [updated_by], [state_id]) VALUES (7, N'CHENNAI', 0, CAST(0x07809FB2166203420B AS DateTime2), CAST(0x0760C4B40F6203420B AS DateTime2), 0, NULL)
INSERT [dbo].[city] ([city_id], [city_name], [created_by], [created_date], [update_date], [updated_by], [state_id]) VALUES (8, N'SILIGURI', 0, CAST(0x07101D1BBC0504420B AS DateTime2), CAST(0x07B06744B70504420B AS DateTime2), 0, NULL)
SET IDENTITY_INSERT [dbo].[city] OFF
SET IDENTITY_INSERT [dbo].[cluster] ON 

INSERT [dbo].[cluster] ([cluster_id], [cluster_name], [created_by], [created_date], [update_date], [updated_by], [web_app_id], [city_id], [country_id], [region_id], [state_id], [web_role_id]) VALUES (7, NULL, 0, CAST(0x07103C50050906420B AS DateTime2), NULL, 0, NULL, NULL, NULL, 1, NULL, 9)
INSERT [dbo].[cluster] ([cluster_id], [cluster_name], [created_by], [created_date], [update_date], [updated_by], [web_app_id], [city_id], [country_id], [region_id], [state_id], [web_role_id]) VALUES (8, NULL, 0, CAST(0x07402253050906420B AS DateTime2), NULL, 0, NULL, NULL, NULL, 2, NULL, 9)
INSERT [dbo].[cluster] ([cluster_id], [cluster_name], [created_by], [created_date], [update_date], [updated_by], [web_app_id], [city_id], [country_id], [region_id], [state_id], [web_role_id]) VALUES (9, NULL, 0, CAST(0x0750BA55050906420B AS DateTime2), NULL, 0, NULL, NULL, NULL, 3, NULL, 9)
INSERT [dbo].[cluster] ([cluster_id], [cluster_name], [created_by], [created_date], [update_date], [updated_by], [web_app_id], [city_id], [country_id], [region_id], [state_id], [web_role_id]) VALUES (10, NULL, 0, CAST(0x07F061AF680906420B AS DateTime2), NULL, 0, NULL, NULL, NULL, 1, NULL, 10)
INSERT [dbo].[cluster] ([cluster_id], [cluster_name], [created_by], [created_date], [update_date], [updated_by], [web_app_id], [city_id], [country_id], [region_id], [state_id], [web_role_id]) VALUES (11, NULL, 0, CAST(0x0730FEAF680906420B AS DateTime2), NULL, 0, NULL, NULL, NULL, 2, NULL, 10)
INSERT [dbo].[cluster] ([cluster_id], [cluster_name], [created_by], [created_date], [update_date], [updated_by], [web_app_id], [city_id], [country_id], [region_id], [state_id], [web_role_id]) VALUES (12, NULL, 0, CAST(0x071021B2680906420B AS DateTime2), NULL, 0, NULL, NULL, NULL, 3, NULL, 10)
INSERT [dbo].[cluster] ([cluster_id], [cluster_name], [created_by], [created_date], [update_date], [updated_by], [web_app_id], [city_id], [country_id], [region_id], [state_id], [web_role_id]) VALUES (13, NULL, 0, CAST(0x0710EC80830906420B AS DateTime2), NULL, 0, NULL, NULL, NULL, 1, NULL, 11)
INSERT [dbo].[cluster] ([cluster_id], [cluster_name], [created_by], [created_date], [update_date], [updated_by], [web_app_id], [city_id], [country_id], [region_id], [state_id], [web_role_id]) VALUES (14, NULL, 0, CAST(0x07D0C082830906420B AS DateTime2), NULL, 0, NULL, NULL, NULL, 2, NULL, 11)
INSERT [dbo].[cluster] ([cluster_id], [cluster_name], [created_by], [created_date], [update_date], [updated_by], [web_app_id], [city_id], [country_id], [region_id], [state_id], [web_role_id]) VALUES (15, NULL, 0, CAST(0x07704784830906420B AS DateTime2), NULL, 0, NULL, NULL, NULL, 3, NULL, 11)
INSERT [dbo].[cluster] ([cluster_id], [cluster_name], [created_by], [created_date], [update_date], [updated_by], [web_app_id], [city_id], [country_id], [region_id], [state_id], [web_role_id]) VALUES (16, NULL, 0, CAST(0x07B03F43C30906420B AS DateTime2), NULL, 0, NULL, NULL, NULL, 1, NULL, 12)
INSERT [dbo].[cluster] ([cluster_id], [cluster_name], [created_by], [created_date], [update_date], [updated_by], [web_app_id], [city_id], [country_id], [region_id], [state_id], [web_role_id]) VALUES (17, NULL, 0, CAST(0x07000344C30906420B AS DateTime2), NULL, 0, NULL, NULL, NULL, 2, NULL, 12)
INSERT [dbo].[cluster] ([cluster_id], [cluster_name], [created_by], [created_date], [update_date], [updated_by], [web_app_id], [city_id], [country_id], [region_id], [state_id], [web_role_id]) VALUES (18, NULL, 0, CAST(0x0760ED44C30906420B AS DateTime2), NULL, 0, NULL, NULL, NULL, 3, NULL, 12)
INSERT [dbo].[cluster] ([cluster_id], [cluster_name], [created_by], [created_date], [update_date], [updated_by], [web_app_id], [city_id], [country_id], [region_id], [state_id], [web_role_id]) VALUES (19, NULL, 0, CAST(0x0750588FFF0906420B AS DateTime2), NULL, 0, NULL, NULL, NULL, 1, NULL, 13)
INSERT [dbo].[cluster] ([cluster_id], [cluster_name], [created_by], [created_date], [update_date], [updated_by], [web_app_id], [city_id], [country_id], [region_id], [state_id], [web_role_id]) VALUES (20, NULL, 0, CAST(0x0790F48FFF0906420B AS DateTime2), NULL, 0, NULL, NULL, NULL, 2, NULL, 13)
INSERT [dbo].[cluster] ([cluster_id], [cluster_name], [created_by], [created_date], [update_date], [updated_by], [web_app_id], [city_id], [country_id], [region_id], [state_id], [web_role_id]) VALUES (21, NULL, 0, CAST(0x07D09090FF0906420B AS DateTime2), NULL, 0, NULL, NULL, NULL, 3, NULL, 13)
INSERT [dbo].[cluster] ([cluster_id], [cluster_name], [created_by], [created_date], [update_date], [updated_by], [web_app_id], [city_id], [country_id], [region_id], [state_id], [web_role_id]) VALUES (22, NULL, 0, CAST(0x0760BA9D3D0A06420B AS DateTime2), NULL, 0, NULL, NULL, NULL, 1, NULL, 14)
INSERT [dbo].[cluster] ([cluster_id], [cluster_name], [created_by], [created_date], [update_date], [updated_by], [web_app_id], [city_id], [country_id], [region_id], [state_id], [web_role_id]) VALUES (23, NULL, 0, CAST(0x07C0A49E3D0A06420B AS DateTime2), NULL, 0, NULL, NULL, NULL, 2, NULL, 14)
INSERT [dbo].[cluster] ([cluster_id], [cluster_name], [created_by], [created_date], [update_date], [updated_by], [web_app_id], [city_id], [country_id], [region_id], [state_id], [web_role_id]) VALUES (24, NULL, 0, CAST(0x07602BA03D0A06420B AS DateTime2), NULL, 0, NULL, NULL, NULL, 3, NULL, 14)
SET IDENTITY_INSERT [dbo].[cluster] OFF
SET IDENTITY_INSERT [dbo].[cluster_user] ON 

INSERT [dbo].[cluster_user] ([cluster_user_id], [created_by], [created_date], [update_date], [updated_by], [user_id], [user_name], [cluster_id]) VALUES (1, 0, CAST(0x070035DBC28B0B420B AS DateTime2), NULL, 0, 2, N'RPHSOUTH1', 16)
INSERT [dbo].[cluster_user] ([cluster_user_id], [created_by], [created_date], [update_date], [updated_by], [user_id], [user_name], [cluster_id]) VALUES (2, 0, CAST(0x07904C5BA3600C420B AS DateTime2), NULL, 0, 2, N'RPHSOUTH1', 11)
SET IDENTITY_INSERT [dbo].[cluster_user] OFF
SET IDENTITY_INSERT [dbo].[country] ON 

INSERT [dbo].[country] ([country_id], [country_name], [created_by], [created_date], [update_date], [updated_by]) VALUES (1, N'INDIA', 0, CAST(0x0760BF35126102420B AS DateTime2), CAST(0x070034DD5F6102420B AS DateTime2), 0)
SET IDENTITY_INSERT [dbo].[country] OFF
SET IDENTITY_INSERT [dbo].[engineer] ON 

INSERT [dbo].[engineer] ([engineer_id], [created_by], [created_on], [email_id], [engineer_name], [mobile_no], [updated_by], [updated_on], [vendor_id], [vendor_name]) VALUES (1, 0, CAST(0x07507A3B489302420B AS DateTime2), N'karthikeyana@titan.co.in', N'Engineer 1', N'9090909090', 0, NULL, 1, NULL)
SET IDENTITY_INSERT [dbo].[engineer] OFF
SET IDENTITY_INSERT [dbo].[equipment] ON 

INSERT [dbo].[equipment] ([equipment_id], [created_by], [created_on], [equipment_code], [equipment_cost], [equipment_name], [updated_by], [updated_on], [equipment_type_id], [vendor_id]) VALUES (1, 0, CAST(0x073078E8189710420B AS DateTime2), N'00AC', 12, N'AC', 0, NULL, 1, NULL)
SET IDENTITY_INSERT [dbo].[equipment] OFF
SET IDENTITY_INSERT [dbo].[equipment_type] ON 

INSERT [dbo].[equipment_type] ([equipment_type_id], [created_by], [created_on], [equipment_type_name], [updated_by], [updated_on]) VALUES (1, 0, CAST(0x07200869D48A02420B AS DateTime2), N'Electrical ', 0, NULL)
INSERT [dbo].[equipment_type] ([equipment_type_id], [created_by], [created_on], [equipment_type_name], [updated_by], [updated_on]) VALUES (2, 0, CAST(0x07F0CCD4C79102420B AS DateTime2), N'Air curtain', 0, NULL)
SET IDENTITY_INSERT [dbo].[equipment_type] OFF
SET IDENTITY_INSERT [dbo].[inventory] ON 

INSERT [dbo].[inventory] ([inventory_id], [created_by], [created_on], [description], [er_no], [installation_date], [manufacture_date], [quantity], [updated_by], [updated_on], [asset_id], [store_id], [vendor_id], [web_master_id], [far_no]) VALUES (1, 0, CAST(0x07F00FE6F19A10420B AS DateTime2), N'rtr', N'56565', NULL, NULL, 1, 0, NULL, 1, 1, NULL, 1, N'12345')
SET IDENTITY_INSERT [dbo].[inventory] OFF
SET IDENTITY_INSERT [dbo].[model] ON 

INSERT [dbo].[model] ([model_id], [created_by], [created_on], [enabled_status], [icon_name], [model_name], [modelurl], [updated_by], [updated_on], [model_no], [brand_id]) VALUES (1, 0, CAST(0x07900AE5359510420B AS DateTime2), NULL, NULL, N'sft', NULL, 0, NULL, N'4555', 2)
SET IDENTITY_INSERT [dbo].[model] OFF
SET IDENTITY_INSERT [dbo].[opertion_type] ON 

INSERT [dbo].[opertion_type] ([opertion_type_id], [opertion_type], [web_role_id]) VALUES (3, N'Country', 9)
INSERT [dbo].[opertion_type] ([opertion_type_id], [opertion_type], [web_role_id]) VALUES (4, N'Country', 10)
INSERT [dbo].[opertion_type] ([opertion_type_id], [opertion_type], [web_role_id]) VALUES (5, N'Country', 11)
INSERT [dbo].[opertion_type] ([opertion_type_id], [opertion_type], [web_role_id]) VALUES (6, N'Region', 12)
INSERT [dbo].[opertion_type] ([opertion_type_id], [opertion_type], [web_role_id]) VALUES (7, N'Region', 13)
INSERT [dbo].[opertion_type] ([opertion_type_id], [opertion_type], [web_role_id]) VALUES (8, N'Region', 14)
SET IDENTITY_INSERT [dbo].[opertion_type] OFF
SET IDENTITY_INSERT [dbo].[owner_type] ON 

INSERT [dbo].[owner_type] ([owner_type_id], [created_by], [created_on], [owner_type_name], [updated_by], [updated_on]) VALUES (1, 0, CAST(0x0700A8832B6702420B AS DateTime2), N'CO', 0, NULL)
INSERT [dbo].[owner_type] ([owner_type_id], [created_by], [created_on], [owner_type_name], [updated_by], [updated_on]) VALUES (2, 0, CAST(0x07C01A2A2F6702420B AS DateTime2), N'FR', 0, NULL)
INSERT [dbo].[owner_type] ([owner_type_id], [created_by], [created_on], [owner_type_name], [updated_by], [updated_on]) VALUES (3, 0, CAST(0x0760D8FB316702420B AS DateTime2), N'MA', 0, NULL)
SET IDENTITY_INSERT [dbo].[owner_type] OFF
SET IDENTITY_INSERT [dbo].[region] ON 

INSERT [dbo].[region] ([region_id], [created_by], [created_date], [region_name], [update_date], [updated_by], [city_id], [state_id]) VALUES (1, 0, CAST(0x0720213E566102420B AS DateTime2), N'SOUTH1', CAST(0x0730AFC1A26102420B AS DateTime2), 0, NULL, 1)
INSERT [dbo].[region] ([region_id], [created_by], [created_date], [region_name], [update_date], [updated_by], [city_id], [state_id]) VALUES (2, 0, CAST(0x0720826C5D6102420B AS DateTime2), N'EAST1', CAST(0x0700084CA96102420B AS DateTime2), 0, NULL, 2)
INSERT [dbo].[region] ([region_id], [created_by], [created_date], [region_name], [update_date], [updated_by], [city_id], [state_id]) VALUES (3, 0, CAST(0x07D0F669D55D04420B AS DateTime2), N'NORTH1', CAST(0x07E0B49E185E04420B AS DateTime2), 0, NULL, 6)
INSERT [dbo].[region] ([region_id], [created_by], [created_date], [region_name], [update_date], [updated_by], [city_id], [state_id]) VALUES (4, 0, CAST(0x07C0D58796780C420B AS DateTime2), N'WEST-1', NULL, 0, NULL, 12)
SET IDENTITY_INSERT [dbo].[region] OFF
SET IDENTITY_INSERT [dbo].[role] ON 

INSERT [dbo].[role] ([role_id], [created_by], [created_on], [enabled_status], [role_name], [updated_by], [updated_on], [access_master_id]) VALUES (3, 1, NULL, 1, N'SuperAdmin', 1, NULL, 2)
INSERT [dbo].[role] ([role_id], [created_by], [created_on], [enabled_status], [role_name], [updated_by], [updated_on], [access_master_id]) VALUES (15, 0, CAST(0x07508BAF620806420B AS DateTime2), 1, N'IT Admin', 1, CAST(0x07508BAF620806420B AS DateTime2), NULL)
INSERT [dbo].[role] ([role_id], [created_by], [created_on], [enabled_status], [role_name], [updated_by], [updated_on], [access_master_id]) VALUES (16, 1, CAST(0x07A02DAC680906420B AS DateTime2), 1, N'HOD', 1, CAST(0x07A02DAC680906420B AS DateTime2), NULL)
INSERT [dbo].[role] ([role_id], [created_by], [created_on], [enabled_status], [role_name], [updated_by], [updated_on], [access_master_id]) VALUES (17, 1, CAST(0x07F02C7E830906420B AS DateTime2), 1, N'CENTRAL HEAD', 1, CAST(0x07F02C7E830906420B AS DateTime2), NULL)
INSERT [dbo].[role] ([role_id], [created_by], [created_on], [enabled_status], [role_name], [updated_by], [updated_on], [access_master_id]) VALUES (18, 1, CAST(0x07908040C30906420B AS DateTime2), 1, N'Regional Project Head', 1, CAST(0x07908040C30906420B AS DateTime2), NULL)
INSERT [dbo].[role] ([role_id], [created_by], [created_on], [enabled_status], [role_name], [updated_by], [updated_on], [access_master_id]) VALUES (19, 1, CAST(0x07E0D58BFF0906420B AS DateTime2), 1, N'Regional Engineer', 1, CAST(0x07E0D58BFF0906420B AS DateTime2), NULL)
INSERT [dbo].[role] ([role_id], [created_by], [created_on], [enabled_status], [role_name], [updated_by], [updated_on], [access_master_id]) VALUES (20, 1, CAST(0x07E09F973D0A06420B AS DateTime2), 1, N'STORE', 1, CAST(0x07E09F973D0A06420B AS DateTime2), NULL)
SET IDENTITY_INSERT [dbo].[role] OFF
SET IDENTITY_INSERT [dbo].[state] ON 

INSERT [dbo].[state] ([state_id], [created_by], [created_date], [state_name], [update_date], [updated_by], [country_id]) VALUES (1, 0, CAST(0x0780BAF21B6102420B AS DateTime2), N'KARNATAKA', CAST(0x0720C8C5686102420B AS DateTime2), 0, 1)
INSERT [dbo].[state] ([state_id], [created_by], [created_date], [state_name], [update_date], [updated_by], [country_id]) VALUES (2, 0, CAST(0x0770D625236102420B AS DateTime2), N'WEST BENGAL', CAST(0x073041BE6E6102420B AS DateTime2), 0, 1)
INSERT [dbo].[state] ([state_id], [created_by], [created_date], [state_name], [update_date], [updated_by], [country_id]) VALUES (3, 0, CAST(0x0780BBAB0F9502420B AS DateTime2), N'KERALA', CAST(0x07807D149F9402420B AS DateTime2), 0, 1)
INSERT [dbo].[state] ([state_id], [created_by], [created_date], [state_name], [update_date], [updated_by], [country_id]) VALUES (4, 0, CAST(0x0750E15E1B9502420B AS DateTime2), N'Assam', CAST(0x073090C7149502420B AS DateTime2), 0, 1)
INSERT [dbo].[state] ([state_id], [created_by], [created_date], [state_name], [update_date], [updated_by], [country_id]) VALUES (5, 0, CAST(0x075052B4229502420B AS DateTime2), N'Sikkim', CAST(0x07D0DB871D9502420B AS DateTime2), 0, 1)
INSERT [dbo].[state] ([state_id], [created_by], [created_date], [state_name], [update_date], [updated_by], [country_id]) VALUES (6, 0, CAST(0x071090045C9502420B AS DateTime2), N'GUJRATH', CAST(0x07F0F44F4E9502420B AS DateTime2), 0, 1)
INSERT [dbo].[state] ([state_id], [created_by], [created_date], [state_name], [update_date], [updated_by], [country_id]) VALUES (7, 0, CAST(0x073006B91A5A03420B AS DateTime2), N'Andra Pradesh', CAST(0x07D05B01145A03420B AS DateTime2), 0, 1)
INSERT [dbo].[state] ([state_id], [created_by], [created_date], [state_name], [update_date], [updated_by], [country_id]) VALUES (8, 0, CAST(0x07900034225A03420B AS DateTime2), N'Delhi', CAST(0x0770BA2C1C5A03420B AS DateTime2), 0, 1)
INSERT [dbo].[state] ([state_id], [created_by], [created_date], [state_name], [update_date], [updated_by], [country_id]) VALUES (9, 0, CAST(0x07C02A48D46B0C420B AS DateTime2), N'UTTAR PRADESH', NULL, 0, 1)
INSERT [dbo].[state] ([state_id], [created_by], [created_date], [state_name], [update_date], [updated_by], [country_id]) VALUES (10, 0, CAST(0x07D0334DD46B0C420B AS DateTime2), N'TAMIL NADU', NULL, 0, 1)
INSERT [dbo].[state] ([state_id], [created_by], [created_date], [state_name], [update_date], [updated_by], [country_id]) VALUES (11, 0, CAST(0x07301E4ED46B0C420B AS DateTime2), N'GUJARAT', NULL, 0, 1)
INSERT [dbo].[state] ([state_id], [created_by], [created_date], [state_name], [update_date], [updated_by], [country_id]) VALUES (12, 0, CAST(0x07D054141C780C420B AS DateTime2), N'MAHARASTRA', CAST(0x074019A1AA780C420B AS DateTime2), 0, 1)
SET IDENTITY_INSERT [dbo].[state] OFF
SET IDENTITY_INSERT [dbo].[store] ON 

INSERT [dbo].[store] ([store_id], [address], [address1], [address2], [costcentre], [created_by], [created_on], [email_id], [fax1], [fax2], [geo_location], [geolocation_name], [latitude], [longitude], [opto_alloted], [owner_name], [phone_no], [phone_no2], [pin_code], [reporting_to], [star_flag], [store_code], [store_flag], [store_locality], [store_name], [store_status], [updated_by], [updated_on], [city_id], [country_id], [owner_type_id], [region_id], [state_id], [store_business_service_type_id], [store_service_type_id], [web_master_id]) VALUES (1, NULL, N'abc', N'', N'0', 0, NULL, N'abc@a.com', N'', N'', N'', N'', N'', N'', NULL, NULL, N'', N'', N'3242355', N'19', 1, N'TS0001', 0, N'local', N'TestStore', NULL, 0, NULL, 1, 1, 1, 1, 1, 1, 3, 1)
SET IDENTITY_INSERT [dbo].[store] OFF
SET IDENTITY_INSERT [dbo].[store_alloted] ON 

INSERT [dbo].[store_alloted] ([store_alloted_id], [created_by], [created_on], [store_alloted_type], [updated_by], [updated_on]) VALUES (1, 0, NULL, N'Equipment', 0, CAST(0x07506FC5AA6610420B AS DateTime2))
INSERT [dbo].[store_alloted] ([store_alloted_id], [created_by], [created_on], [store_alloted_type], [updated_by], [updated_on]) VALUES (2, 0, NULL, N'Optometrist', 0, CAST(0x0720635DAE6610420B AS DateTime2))
SET IDENTITY_INSERT [dbo].[store_alloted] OFF
SET IDENTITY_INSERT [dbo].[store_alloted_details] ON 

INSERT [dbo].[store_alloted_details] ([store_alloted_details_id], [created_by], [created_on], [updated_by], [updated_on], [store_id], [store_alloted_id]) VALUES (1, 0, NULL, 0, NULL, 1, 1)
SET IDENTITY_INSERT [dbo].[store_alloted_details] OFF
SET IDENTITY_INSERT [dbo].[store_business_service_type] ON 

INSERT [dbo].[store_business_service_type] ([store_business_service_type_id], [created_by], [created_on], [store_business_service_type_name], [updated_by], [updated_on]) VALUES (1, 0, NULL, N'Service Type', 0, CAST(0x078035EFC36902420B AS DateTime2))
INSERT [dbo].[store_business_service_type] ([store_business_service_type_id], [created_by], [created_on], [store_business_service_type_name], [updated_by], [updated_on]) VALUES (2, 0, NULL, N'Sales Type', 0, CAST(0x07A0A3C5D76902420B AS DateTime2))
INSERT [dbo].[store_business_service_type] ([store_business_service_type_id], [created_by], [created_on], [store_business_service_type_name], [updated_by], [updated_on]) VALUES (3, 0, NULL, N'Trading Type', 0, CAST(0x07F00955E26902420B AS DateTime2))
INSERT [dbo].[store_business_service_type] ([store_business_service_type_id], [created_by], [created_on], [store_business_service_type_name], [updated_by], [updated_on]) VALUES (4, 0, CAST(0x07606F2E226702420B AS DateTime2), N'Taneira', 0, NULL)
SET IDENTITY_INSERT [dbo].[store_business_service_type] OFF
SET IDENTITY_INSERT [dbo].[store_service_type] ON 

INSERT [dbo].[store_service_type] ([store_service_type_id], [created_by], [created_on], [sla], [store_service_type_name], [updated_by], [updated_on]) VALUES (1, 0, NULL, N'W001', N'Watch retail showroom', 0, CAST(0x07300BC98F9302420B AS DateTime2))
INSERT [dbo].[store_service_type] ([store_service_type_id], [created_by], [created_on], [sla], [store_service_type_name], [updated_by], [updated_on]) VALUES (2, 0, NULL, N'T001', N'Tanishq boutique', 0, CAST(0x07C0E34D9D9302420B AS DateTime2))
INSERT [dbo].[store_service_type] ([store_service_type_id], [created_by], [created_on], [sla], [store_service_type_name], [updated_by], [updated_on]) VALUES (3, 0, NULL, N'E001', N'TITAN EYE PLUS', 0, CAST(0x0770AAE8A99302420B AS DateTime2))
INSERT [dbo].[store_service_type] ([store_service_type_id], [created_by], [created_on], [sla], [store_service_type_name], [updated_by], [updated_on]) VALUES (4, 0, CAST(0x0770EF9A9E8E04420B AS DateTime2), N'3', N'METRO', 0, NULL)
INSERT [dbo].[store_service_type] ([store_service_type_id], [created_by], [created_on], [sla], [store_service_type_name], [updated_by], [updated_on]) VALUES (5, 0, CAST(0x07103908A78E04420B AS DateTime2), N'5', N'NON-METRO', 0, NULL)
INSERT [dbo].[store_service_type] ([store_service_type_id], [created_by], [created_on], [sla], [store_service_type_name], [updated_by], [updated_on]) VALUES (6, 0, CAST(0x07A0D270B08E04420B AS DateTime2), N'9', N'NORTH EAST', 0, NULL)
SET IDENTITY_INSERT [dbo].[store_service_type] OFF
SET IDENTITY_INSERT [dbo].[uploaded_document] ON 

INSERT [dbo].[uploaded_document] ([uploaded_document_id], [content_type], [description], [file_upload_type], [filename], [name], [processed], [role_id], [uploaded_date], [time_stamp], [uploaded_user_id], [uploaded_user_name]) VALUES (1, N'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet', NULL, 14, N'State (1).xlsx', N'State = 1610695309866', 0, NULL, CAST(0x0C420B00 AS Date), CAST(0x07A0C4D4D26B0C420B AS DateTime2), NULL, NULL)
INSERT [dbo].[uploaded_document] ([uploaded_document_id], [content_type], [description], [file_upload_type], [filename], [name], [processed], [role_id], [uploaded_date], [time_stamp], [uploaded_user_id], [uploaded_user_name]) VALUES (2, N'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet', NULL, 14, N'State (1).xlsx', N'State = 1610695351365', 0, NULL, CAST(0x0C420B00 AS Date), CAST(0x07500391EB6B0C420B AS DateTime2), NULL, NULL)
INSERT [dbo].[uploaded_document] ([uploaded_document_id], [content_type], [description], [file_upload_type], [filename], [name], [processed], [role_id], [uploaded_date], [time_stamp], [uploaded_user_id], [uploaded_user_name]) VALUES (3, N'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet', NULL, 15, N'Region (2).xlsx', N'Region = 1610700662017', 0, NULL, CAST(0x0C420B00 AS Date), CAST(0x071033F648780C420B AS DateTime2), NULL, NULL)
INSERT [dbo].[uploaded_document] ([uploaded_document_id], [content_type], [description], [file_upload_type], [filename], [name], [processed], [role_id], [uploaded_date], [time_stamp], [uploaded_user_id], [uploaded_user_name]) VALUES (4, N'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet', NULL, 15, N'Region (1).xlsx', N'Region = 1610700719046', 0, NULL, CAST(0x0C420B00 AS Date), CAST(0x076022F46A780C420B AS DateTime2), NULL, NULL)
INSERT [dbo].[uploaded_document] ([uploaded_document_id], [content_type], [description], [file_upload_type], [filename], [name], [processed], [role_id], [uploaded_date], [time_stamp], [uploaded_user_id], [uploaded_user_name]) VALUES (5, N'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet', NULL, 15, N'Region (1).xlsx', N'Region = 1610700792006', 0, NULL, CAST(0x0C420B00 AS Date), CAST(0x0760F27096780C420B AS DateTime2), NULL, NULL)
SET IDENTITY_INSERT [dbo].[uploaded_document] OFF
SET IDENTITY_INSERT [dbo].[users] ON 

INSERT [dbo].[users] ([id], [created_by], [created_on], [modified_by], [modified_on], [account_non_expired], [account_non_locked], [credentials_non_expired], [is_enabled], [last_login], [login_count], [password], [username], [web_role_id], [address], [discription], [email], [phone_no], [first_name], [last_name]) VALUES (1, 1, NULL, N'1', NULL, 1, 1, 1, 1, NULL, NULL, N'17c4520f6cfd1ab53d8745e84681eb49', N'superadmin', 1, NULL, NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[users] ([id], [created_by], [created_on], [modified_by], [modified_on], [account_non_expired], [account_non_locked], [credentials_non_expired], [is_enabled], [last_login], [login_count], [password], [username], [web_role_id], [address], [discription], [email], [phone_no], [first_name], [last_name]) VALUES (2, 1, CAST(0x076080B7C28B0B420B AS DateTime2), NULL, CAST(0x0730A0CADB600C420B AS DateTime2), 1, 1, 1, 1, NULL, NULL, N'f765f01e1f1be870113e20c4c0b80cff', N'RPHSOUTH1', 10, N'bang', N'bbb', N'sandeep@rim-global.com', N'8088497911', N'Sandeep', N'Das')
INSERT [dbo].[users] ([id], [created_by], [created_on], [modified_by], [modified_on], [account_non_expired], [account_non_locked], [credentials_non_expired], [is_enabled], [last_login], [login_count], [password], [username], [web_role_id], [address], [discription], [email], [phone_no], [first_name], [last_name]) VALUES (3, 1, CAST(0x0790F82BF2580C420B AS DateTime2), NULL, CAST(0x07701CB1F6610C420B AS DateTime2), 1, 1, 1, 1, NULL, NULL, N'c7dad8b8e795a1a9b74e15b056cb05d8', N'IRSG App Admin', 2, N'Integrity', N'Corporate office, Bangalore', N'prabakaranr@titan.co.in', N'9898989000', N'Prabhakaran', N'R')
SET IDENTITY_INSERT [dbo].[users] OFF
SET IDENTITY_INSERT [dbo].[vendor] ON 

INSERT [dbo].[vendor] ([vendor_id], [billing_address], [billing_email_id], [contact_number], [contact_person], [created_by], [created_on], [pin_code], [service_address1], [service_address2], [service_email_id1], [service_email_id2], [updated_by], [updated_on], [vendor_code], [vendor_name], [vendor_status], [city_id], [vendor_type_id], [web_master_id]) VALUES (1, N'Bengaluru', N'karthikeyana@titan.co.in', N'9898989898', N'Vendor person 1', 0, NULL, N'560100', N'karthikeyana@titan.co.in', N'', N'karthikeyana@titan.co.in', N'', 0, CAST(0x0760CF46379302420B AS DateTime2), N'900001', N'Genset service ltd', N'ACTIVE', 1, 5, 1)
SET IDENTITY_INSERT [dbo].[vendor] OFF
SET IDENTITY_INSERT [dbo].[vendor_type] ON 

INSERT [dbo].[vendor_type] ([vendor_type_id], [created_by], [created_on], [updated_by], [updated_on], [vendor_type_name]) VALUES (1, 0, CAST(0x07D093855C7D02420B AS DateTime2), 0, NULL, N'Furniture repairing ')
INSERT [dbo].[vendor_type] ([vendor_type_id], [created_by], [created_on], [updated_by], [updated_on], [vendor_type_name]) VALUES (2, 0, CAST(0x07C0D24A637D02420B AS DateTime2), 0, NULL, N'Air condition service')
INSERT [dbo].[vendor_type] ([vendor_type_id], [created_by], [created_on], [updated_by], [updated_on], [vendor_type_name]) VALUES (3, 0, CAST(0x07803987787D02420B AS DateTime2), 0, NULL, N'Wiring and electrical')
INSERT [dbo].[vendor_type] ([vendor_type_id], [created_by], [created_on], [updated_by], [updated_on], [vendor_type_name]) VALUES (4, 0, CAST(0x072083B88A7D02420B AS DateTime2), 0, NULL, N'CCTV and camera install & service')
INSERT [dbo].[vendor_type] ([vendor_type_id], [created_by], [created_on], [updated_by], [updated_on], [vendor_type_name]) VALUES (5, 0, CAST(0x0720D9FC9F7D02420B AS DateTime2), 0, NULL, N'DC Generator service')
SET IDENTITY_INSERT [dbo].[vendor_type] OFF
SET IDENTITY_INSERT [dbo].[web_master] ON 

INSERT [dbo].[web_master] ([web_master_id], [created_by], [created_on], [enabled_status], [updated_by], [updated_on], [web_master_name]) VALUES (1, 0, CAST(0x07A0303EDA5C02420B AS DateTime2), 0, 0, CAST(0x07A0303EDA5C02420B AS DateTime2), N'IRSG')
INSERT [dbo].[web_master] ([web_master_id], [created_by], [created_on], [enabled_status], [updated_by], [updated_on], [web_master_name]) VALUES (2, 0, CAST(0x0750BF5D7A5E02420B AS DateTime2), 0, 0, CAST(0x0750BF5D7A5E02420B AS DateTime2), N'EYEWEAR')
INSERT [dbo].[web_master] ([web_master_id], [created_by], [created_on], [enabled_status], [updated_by], [updated_on], [web_master_name]) VALUES (3, 0, CAST(0x07D0EFF47D5E02420B AS DateTime2), 0, 0, CAST(0x07D0EFF47D5E02420B AS DateTime2), N'WATCH')
INSERT [dbo].[web_master] ([web_master_id], [created_by], [created_on], [enabled_status], [updated_by], [updated_on], [web_master_name]) VALUES (4, 0, CAST(0x07605C4A815E02420B AS DateTime2), 0, 0, CAST(0x07605C4A815E02420B AS DateTime2), N'TANISHQ')
INSERT [dbo].[web_master] ([web_master_id], [created_by], [created_on], [enabled_status], [updated_by], [updated_on], [web_master_name]) VALUES (5, 0, NULL, 0, 0, CAST(0x07C0F544690A06420B AS DateTime2), N'XYZ')
SET IDENTITY_INSERT [dbo].[web_master] OFF
SET IDENTITY_INSERT [dbo].[web_role] ON 

INSERT [dbo].[web_role] ([web_role_id], [created_by], [created_on], [enabled_status], [updated_by], [updated_on], [reporting_id], [role_id], [access_master_id]) VALUES (1, 1, NULL, 1, 1, CAST(0x0790CB97310806420B AS DateTime2), 3, 3, 1)
INSERT [dbo].[web_role] ([web_role_id], [created_by], [created_on], [enabled_status], [updated_by], [updated_on], [reporting_id], [role_id], [access_master_id]) VALUES (2, 1, NULL, 1, 1, CAST(0x07A030B5620806420B AS DateTime2), 3, 15, 1)
INSERT [dbo].[web_role] ([web_role_id], [created_by], [created_on], [enabled_status], [updated_by], [updated_on], [reporting_id], [role_id], [access_master_id]) VALUES (3, 1, NULL, 1, 1, NULL, 3, 3, 2)
INSERT [dbo].[web_role] ([web_role_id], [created_by], [created_on], [enabled_status], [updated_by], [updated_on], [reporting_id], [role_id], [access_master_id]) VALUES (4, 1, NULL, 1, 1, CAST(0x07A01B02F30806420B AS DateTime2), 3, 15, 2)
INSERT [dbo].[web_role] ([web_role_id], [created_by], [created_on], [enabled_status], [updated_by], [updated_on], [reporting_id], [role_id], [access_master_id]) VALUES (5, 1, NULL, 1, 1, NULL, 3, 3, 4)
INSERT [dbo].[web_role] ([web_role_id], [created_by], [created_on], [enabled_status], [updated_by], [updated_on], [reporting_id], [role_id], [access_master_id]) VALUES (9, 1, CAST(0x07B0E04C050906420B AS DateTime2), 1, 1, CAST(0x07B0E04C050906420B AS DateTime2), 3, 15, 4)
INSERT [dbo].[web_role] ([web_role_id], [created_by], [created_on], [enabled_status], [updated_by], [updated_on], [reporting_id], [role_id], [access_master_id]) VALUES (10, 1, CAST(0x07308DAD680906420B AS DateTime2), 1, 1, CAST(0x07308DAD680906420B AS DateTime2), 15, 16, 1)
INSERT [dbo].[web_role] ([web_role_id], [created_by], [created_on], [enabled_status], [updated_by], [updated_on], [reporting_id], [role_id], [access_master_id]) VALUES (11, 1, CAST(0x07808C7F830906420B AS DateTime2), 1, 1, CAST(0x07808C7F830906420B AS DateTime2), 16, 17, 1)
INSERT [dbo].[web_role] ([web_role_id], [created_by], [created_on], [enabled_status], [updated_by], [updated_on], [reporting_id], [role_id], [access_master_id]) VALUES (12, 1, CAST(0x07300742C30906420B AS DateTime2), 1, 1, CAST(0x07300742C30906420B AS DateTime2), 17, 18, 1)
INSERT [dbo].[web_role] ([web_role_id], [created_by], [created_on], [enabled_status], [updated_by], [updated_on], [reporting_id], [role_id], [access_master_id]) VALUES (13, 1, CAST(0x0770358DFF0906420B AS DateTime2), 1, 1, CAST(0x0770358DFF0906420B AS DateTime2), 18, 19, 1)
INSERT [dbo].[web_role] ([web_role_id], [created_by], [created_on], [enabled_status], [updated_by], [updated_on], [reporting_id], [role_id], [access_master_id]) VALUES (14, 1, CAST(0x0770FF983D0A06420B AS DateTime2), 1, 1, CAST(0x0770FF983D0A06420B AS DateTime2), 19, 20, 1)
SET IDENTITY_INSERT [dbo].[web_role] OFF
SET ANSI_PADDING ON

GO
/****** Object:  Index [UK_tb58bjumlnu42candq23htm8e]    Script Date: 1/24/2021 2:21:36 PM ******/
ALTER TABLE [dbo].[application_access] ADD  CONSTRAINT [UK_tb58bjumlnu42candq23htm8e] UNIQUE NONCLUSTERED 
(
	[application_access_name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [UK_775j7ye2oj0wefvjjkyykyhuy]    Script Date: 1/24/2021 2:21:36 PM ******/
ALTER TABLE [dbo].[brand] ADD  CONSTRAINT [UK_775j7ye2oj0wefvjjkyykyhuy] UNIQUE NONCLUSTERED 
(
	[brand_code] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [UK_djnk44fptegbsu6drhc9xvlfj]    Script Date: 1/24/2021 2:21:36 PM ******/
ALTER TABLE [dbo].[city] ADD  CONSTRAINT [UK_djnk44fptegbsu6drhc9xvlfj] UNIQUE NONCLUSTERED 
(
	[city_name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [UK_qrkn270121aljmucrdbnmd35p]    Script Date: 1/24/2021 2:21:36 PM ******/
ALTER TABLE [dbo].[country] ADD  CONSTRAINT [UK_qrkn270121aljmucrdbnmd35p] UNIQUE NONCLUSTERED 
(
	[country_name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [UK_dnns7ljpj8pp1683296xl9lgb]    Script Date: 1/24/2021 2:21:36 PM ******/
ALTER TABLE [dbo].[engineer] ADD  CONSTRAINT [UK_dnns7ljpj8pp1683296xl9lgb] UNIQUE NONCLUSTERED 
(
	[mobile_no] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [UK_45nvhgtkn6sjr66ml4g1hws1p]    Script Date: 1/24/2021 2:21:36 PM ******/
ALTER TABLE [dbo].[equipment] ADD  CONSTRAINT [UK_45nvhgtkn6sjr66ml4g1hws1p] UNIQUE NONCLUSTERED 
(
	[equipment_code] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [UK_tgdq6gtinbci7x759dr24gpoa]    Script Date: 1/24/2021 2:21:36 PM ******/
ALTER TABLE [dbo].[equipment] ADD  CONSTRAINT [UK_tgdq6gtinbci7x759dr24gpoa] UNIQUE NONCLUSTERED 
(
	[equipment_name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [UK_cd1d3mqq0me5xs3stwjntka24]    Script Date: 1/24/2021 2:21:36 PM ******/
ALTER TABLE [dbo].[equipment_type] ADD  CONSTRAINT [UK_cd1d3mqq0me5xs3stwjntka24] UNIQUE NONCLUSTERED 
(
	[equipment_type_name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [UK_gqa31umtnrjdnp8r5vrh44doa]    Script Date: 1/24/2021 2:21:36 PM ******/
ALTER TABLE [dbo].[feature] ADD  CONSTRAINT [UK_gqa31umtnrjdnp8r5vrh44doa] UNIQUE NONCLUSTERED 
(
	[feature_name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [UK_fkbphcrvfuxym512lp2qmx9kl]    Script Date: 1/24/2021 2:21:36 PM ******/
ALTER TABLE [dbo].[futures] ADD  CONSTRAINT [UK_fkbphcrvfuxym512lp2qmx9kl] UNIQUE NONCLUSTERED 
(
	[future_name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [UK_5ilarkkifs5soy92byivbcsn3]    Script Date: 1/24/2021 2:21:36 PM ******/
ALTER TABLE [dbo].[model] ADD  CONSTRAINT [UK_5ilarkkifs5soy92byivbcsn3] UNIQUE NONCLUSTERED 
(
	[model_no] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [UK_6tnk56bg7qssdxlnatj4cnhm]    Script Date: 1/24/2021 2:21:36 PM ******/
ALTER TABLE [dbo].[model] ADD  CONSTRAINT [UK_6tnk56bg7qssdxlnatj4cnhm] UNIQUE NONCLUSTERED 
(
	[model_name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [UK_ao25w2hy445wesxeo9wfvsp77]    Script Date: 1/24/2021 2:21:36 PM ******/
ALTER TABLE [dbo].[module] ADD  CONSTRAINT [UK_ao25w2hy445wesxeo9wfvsp77] UNIQUE NONCLUSTERED 
(
	[module_name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [UK_e2f951r3gu9kveikwojv5g9t]    Script Date: 1/24/2021 2:21:36 PM ******/
ALTER TABLE [dbo].[module] ADD  CONSTRAINT [UK_e2f951r3gu9kveikwojv5g9t] UNIQUE NONCLUSTERED 
(
	[model_name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [UK_bstmen2pk463hcijuosun6g72]    Script Date: 1/24/2021 2:21:36 PM ******/
ALTER TABLE [dbo].[region] ADD  CONSTRAINT [UK_bstmen2pk463hcijuosun6g72] UNIQUE NONCLUSTERED 
(
	[region_name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [UK_iubw515ff0ugtm28p8g3myt0h]    Script Date: 1/24/2021 2:21:36 PM ******/
ALTER TABLE [dbo].[role] ADD  CONSTRAINT [UK_iubw515ff0ugtm28p8g3myt0h] UNIQUE NONCLUSTERED 
(
	[role_name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [UK_qtjsbpmp2ejq0753ktldenyqo]    Script Date: 1/24/2021 2:21:36 PM ******/
ALTER TABLE [dbo].[state] ADD  CONSTRAINT [UK_qtjsbpmp2ejq0753ktldenyqo] UNIQUE NONCLUSTERED 
(
	[state_name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [UK_kx1xg484de7gts484p740pb95]    Script Date: 1/24/2021 2:21:36 PM ******/
ALTER TABLE [dbo].[store] ADD  CONSTRAINT [UK_kx1xg484de7gts484p740pb95] UNIQUE NONCLUSTERED 
(
	[costcentre] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [UK_lls6u48yyrknu4gjf4l4f35t7]    Script Date: 1/24/2021 2:21:36 PM ******/
ALTER TABLE [dbo].[store] ADD  CONSTRAINT [UK_lls6u48yyrknu4gjf4l4f35t7] UNIQUE NONCLUSTERED 
(
	[store_code] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [UK_9gu8hoagcf2xnmry57ljd13rj]    Script Date: 1/24/2021 2:21:36 PM ******/
ALTER TABLE [dbo].[sub_features] ADD  CONSTRAINT [UK_9gu8hoagcf2xnmry57ljd13rj] UNIQUE NONCLUSTERED 
(
	[sub_feature_name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [UK_nhe6rrov20wg1apq6tfre55lx]    Script Date: 1/24/2021 2:21:36 PM ******/
ALTER TABLE [dbo].[sub_futures] ADD  CONSTRAINT [UK_nhe6rrov20wg1apq6tfre55lx] UNIQUE NONCLUSTERED 
(
	[sub_future_name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [UK_c755wh63hxhlluqpxw7smwi2q]    Script Date: 1/24/2021 2:21:36 PM ******/
ALTER TABLE [dbo].[sub_model] ADD  CONSTRAINT [UK_c755wh63hxhlluqpxw7smwi2q] UNIQUE NONCLUSTERED 
(
	[sub_model_name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [UK_1fu71ycp6pvlv177ylvbw75p3]    Script Date: 1/24/2021 2:21:36 PM ******/
ALTER TABLE [dbo].[sub_module] ADD  CONSTRAINT [UK_1fu71ycp6pvlv177ylvbw75p3] UNIQUE NONCLUSTERED 
(
	[sub_model_name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [UK_hhbsc3n09bhljhwphi04fljkf]    Script Date: 1/24/2021 2:21:36 PM ******/
ALTER TABLE [dbo].[sub_module] ADD  CONSTRAINT [UK_hhbsc3n09bhljhwphi04fljkf] UNIQUE NONCLUSTERED 
(
	[sub_module_name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [UK_r43af9ap4edm43mmtq01oddj6]    Script Date: 1/24/2021 2:21:36 PM ******/
ALTER TABLE [dbo].[users] ADD  CONSTRAINT [UK_r43af9ap4edm43mmtq01oddj6] UNIQUE NONCLUSTERED 
(
	[username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [UK_abema2gvm8psw0jabn5wfd0gd]    Script Date: 1/24/2021 2:21:36 PM ******/
ALTER TABLE [dbo].[vendor] ADD  CONSTRAINT [UK_abema2gvm8psw0jabn5wfd0gd] UNIQUE NONCLUSTERED 
(
	[vendor_code] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [UK_94jqqxokavo6tcbblp9rsqc3]    Script Date: 1/24/2021 2:21:36 PM ******/
ALTER TABLE [dbo].[vendor_type] ADD  CONSTRAINT [UK_94jqqxokavo6tcbblp9rsqc3] UNIQUE NONCLUSTERED 
(
	[vendor_type_name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [UK_5hsroi9vcb7t73h7arprfkwas]    Script Date: 1/24/2021 2:21:36 PM ******/
ALTER TABLE [dbo].[web_master] ADD  CONSTRAINT [UK_5hsroi9vcb7t73h7arprfkwas] UNIQUE NONCLUSTERED 
(
	[web_master_name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
ALTER TABLE [dbo].[accesspolicy_domain]  WITH CHECK ADD  CONSTRAINT [FK1p6sie2jlkvnx0qt3dsasdgis] FOREIGN KEY([web_role_id])
REFERENCES [dbo].[web_role] ([web_role_id])
GO
ALTER TABLE [dbo].[accesspolicy_domain] CHECK CONSTRAINT [FK1p6sie2jlkvnx0qt3dsasdgis]
GO
ALTER TABLE [dbo].[accesspolicy_domain]  WITH CHECK ADD  CONSTRAINT [FKcnxwasexcq7r1nm55yatvh977] FOREIGN KEY([feature_id])
REFERENCES [dbo].[feature] ([feature_id])
GO
ALTER TABLE [dbo].[accesspolicy_domain] CHECK CONSTRAINT [FKcnxwasexcq7r1nm55yatvh977]
GO
ALTER TABLE [dbo].[accesspolicy_domain]  WITH CHECK ADD  CONSTRAINT [FKd2jhavpfwp0mp21in2ldvnxrf] FOREIGN KEY([sub_module_id])
REFERENCES [dbo].[sub_module] ([sub_model_id])
GO
ALTER TABLE [dbo].[accesspolicy_domain] CHECK CONSTRAINT [FKd2jhavpfwp0mp21in2ldvnxrf]
GO
ALTER TABLE [dbo].[accesspolicy_domain]  WITH CHECK ADD  CONSTRAINT [FKgnpaax4srf9y07a4ef91kbwah] FOREIGN KEY([sub_feature_id])
REFERENCES [dbo].[sub_features] ([sub_feature_id])
GO
ALTER TABLE [dbo].[accesspolicy_domain] CHECK CONSTRAINT [FKgnpaax4srf9y07a4ef91kbwah]
GO
ALTER TABLE [dbo].[accesspolicy_domain]  WITH CHECK ADD  CONSTRAINT [FKhna3t6g3wm1h8a5we4ii2emwj] FOREIGN KEY([module_id])
REFERENCES [dbo].[module] ([model_id])
GO
ALTER TABLE [dbo].[accesspolicy_domain] CHECK CONSTRAINT [FKhna3t6g3wm1h8a5we4ii2emwj]
GO
ALTER TABLE [dbo].[asset]  WITH CHECK ADD  CONSTRAINT [FKcg3quk28oir8pb0aihfj3t0xa] FOREIGN KEY([web_master_id])
REFERENCES [dbo].[web_master] ([web_master_id])
GO
ALTER TABLE [dbo].[asset] CHECK CONSTRAINT [FKcg3quk28oir8pb0aihfj3t0xa]
GO
ALTER TABLE [dbo].[asset]  WITH CHECK ADD  CONSTRAINT [FKhnnsbsgatba9u776mpms16kgv] FOREIGN KEY([equipment_id])
REFERENCES [dbo].[equipment] ([equipment_id])
GO
ALTER TABLE [dbo].[asset] CHECK CONSTRAINT [FKhnnsbsgatba9u776mpms16kgv]
GO
ALTER TABLE [dbo].[asset]  WITH CHECK ADD  CONSTRAINT [FKnnllll76a7qkpepmucx557o42] FOREIGN KEY([model_id])
REFERENCES [dbo].[model] ([model_id])
GO
ALTER TABLE [dbo].[asset] CHECK CONSTRAINT [FKnnllll76a7qkpepmucx557o42]
GO
ALTER TABLE [dbo].[city]  WITH CHECK ADD  CONSTRAINT [FK6p2u50v8fg2y0js6djc6xanit] FOREIGN KEY([state_id])
REFERENCES [dbo].[state] ([state_id])
GO
ALTER TABLE [dbo].[city] CHECK CONSTRAINT [FK6p2u50v8fg2y0js6djc6xanit]
GO
ALTER TABLE [dbo].[cluster]  WITH CHECK ADD  CONSTRAINT [FKc2epegy8qj2c0sgxk9xd3pxj0] FOREIGN KEY([city_id])
REFERENCES [dbo].[city] ([city_id])
GO
ALTER TABLE [dbo].[cluster] CHECK CONSTRAINT [FKc2epegy8qj2c0sgxk9xd3pxj0]
GO
ALTER TABLE [dbo].[cluster]  WITH CHECK ADD  CONSTRAINT [FKgiuvmuvvjr51ehx9gvq4kuuma] FOREIGN KEY([state_id])
REFERENCES [dbo].[state] ([state_id])
GO
ALTER TABLE [dbo].[cluster] CHECK CONSTRAINT [FKgiuvmuvvjr51ehx9gvq4kuuma]
GO
ALTER TABLE [dbo].[cluster]  WITH CHECK ADD  CONSTRAINT [FKh5y0gwevtvgxy259400l9i5tg] FOREIGN KEY([country_id])
REFERENCES [dbo].[country] ([country_id])
GO
ALTER TABLE [dbo].[cluster] CHECK CONSTRAINT [FKh5y0gwevtvgxy259400l9i5tg]
GO
ALTER TABLE [dbo].[cluster]  WITH CHECK ADD  CONSTRAINT [FKki43adxvto81q06jbxjaypj7g] FOREIGN KEY([web_role_id])
REFERENCES [dbo].[web_role] ([web_role_id])
GO
ALTER TABLE [dbo].[cluster] CHECK CONSTRAINT [FKki43adxvto81q06jbxjaypj7g]
GO
ALTER TABLE [dbo].[cluster]  WITH CHECK ADD  CONSTRAINT [FKtnadmwaq81hwpo8cl1ojl5xwb] FOREIGN KEY([region_id])
REFERENCES [dbo].[region] ([region_id])
GO
ALTER TABLE [dbo].[cluster] CHECK CONSTRAINT [FKtnadmwaq81hwpo8cl1ojl5xwb]
GO
ALTER TABLE [dbo].[cluster_user]  WITH CHECK ADD  CONSTRAINT [FKb53aebifsp0wsc4l3e77qlf0] FOREIGN KEY([cluster_id])
REFERENCES [dbo].[cluster] ([cluster_id])
GO
ALTER TABLE [dbo].[cluster_user] CHECK CONSTRAINT [FKb53aebifsp0wsc4l3e77qlf0]
GO
ALTER TABLE [dbo].[equipment]  WITH CHECK ADD  CONSTRAINT [FKkym9rv6eag0qx0m5bjp6gw8sv] FOREIGN KEY([vendor_id])
REFERENCES [dbo].[vendor] ([vendor_id])
GO
ALTER TABLE [dbo].[equipment] CHECK CONSTRAINT [FKkym9rv6eag0qx0m5bjp6gw8sv]
GO
ALTER TABLE [dbo].[equipment]  WITH CHECK ADD  CONSTRAINT [FKog1e3ls88y65004cs34gtncgs] FOREIGN KEY([equipment_type_id])
REFERENCES [dbo].[equipment_type] ([equipment_type_id])
GO
ALTER TABLE [dbo].[equipment] CHECK CONSTRAINT [FKog1e3ls88y65004cs34gtncgs]
GO
ALTER TABLE [dbo].[feature]  WITH CHECK ADD  CONSTRAINT [FKg0ahyfdr88sxvbsn7cveh2lod] FOREIGN KEY([sub_module_id])
REFERENCES [dbo].[sub_module] ([sub_model_id])
GO
ALTER TABLE [dbo].[feature] CHECK CONSTRAINT [FKg0ahyfdr88sxvbsn7cveh2lod]
GO
ALTER TABLE [dbo].[futures]  WITH CHECK ADD  CONSTRAINT [FKlakbklxw0f66in0e9mpttiq3o] FOREIGN KEY([sub_model_id])
REFERENCES [dbo].[sub_module] ([sub_model_id])
GO
ALTER TABLE [dbo].[futures] CHECK CONSTRAINT [FKlakbklxw0f66in0e9mpttiq3o]
GO
ALTER TABLE [dbo].[futures]  WITH CHECK ADD  CONSTRAINT [FKry7ecacd37qiq0886ae0h68k4] FOREIGN KEY([sub_model_id])
REFERENCES [dbo].[sub_model] ([sub_model_id])
GO
ALTER TABLE [dbo].[futures] CHECK CONSTRAINT [FKry7ecacd37qiq0886ae0h68k4]
GO
ALTER TABLE [dbo].[inventory]  WITH CHECK ADD  CONSTRAINT [FK62b3lar0yy9w901phi2fja4t7] FOREIGN KEY([asset_id])
REFERENCES [dbo].[asset] ([asset_id])
GO
ALTER TABLE [dbo].[inventory] CHECK CONSTRAINT [FK62b3lar0yy9w901phi2fja4t7]
GO
ALTER TABLE [dbo].[inventory]  WITH CHECK ADD  CONSTRAINT [FKcscvovkb3lb3o7maefjkpaw3h] FOREIGN KEY([vendor_id])
REFERENCES [dbo].[vendor] ([vendor_id])
GO
ALTER TABLE [dbo].[inventory] CHECK CONSTRAINT [FKcscvovkb3lb3o7maefjkpaw3h]
GO
ALTER TABLE [dbo].[inventory]  WITH CHECK ADD  CONSTRAINT [FKdjc0hd9c1y11cnfr7bgcerw1v] FOREIGN KEY([web_master_id])
REFERENCES [dbo].[web_master] ([web_master_id])
GO
ALTER TABLE [dbo].[inventory] CHECK CONSTRAINT [FKdjc0hd9c1y11cnfr7bgcerw1v]
GO
ALTER TABLE [dbo].[inventory]  WITH CHECK ADD  CONSTRAINT [FKtdgy352s88shlsdbhxqp5k9vk] FOREIGN KEY([store_id])
REFERENCES [dbo].[store] ([store_id])
GO
ALTER TABLE [dbo].[inventory] CHECK CONSTRAINT [FKtdgy352s88shlsdbhxqp5k9vk]
GO
ALTER TABLE [dbo].[model]  WITH CHECK ADD  CONSTRAINT [FKhbgv4j3vpt308sepyq9q79mhu] FOREIGN KEY([brand_id])
REFERENCES [dbo].[brand] ([brand_id])
GO
ALTER TABLE [dbo].[model] CHECK CONSTRAINT [FKhbgv4j3vpt308sepyq9q79mhu]
GO
ALTER TABLE [dbo].[opertion_type]  WITH CHECK ADD  CONSTRAINT [FK4991wqwab58n3hrsbvhsfj5sf] FOREIGN KEY([web_role_id])
REFERENCES [dbo].[web_role] ([web_role_id])
GO
ALTER TABLE [dbo].[opertion_type] CHECK CONSTRAINT [FK4991wqwab58n3hrsbvhsfj5sf]
GO
ALTER TABLE [dbo].[region]  WITH CHECK ADD  CONSTRAINT [FK7bn3ogohqe1tach1arbdvropi] FOREIGN KEY([state_id])
REFERENCES [dbo].[state] ([state_id])
GO
ALTER TABLE [dbo].[region] CHECK CONSTRAINT [FK7bn3ogohqe1tach1arbdvropi]
GO
ALTER TABLE [dbo].[region]  WITH CHECK ADD  CONSTRAINT [FKedafr6k537xtdes7gc7nq778a] FOREIGN KEY([city_id])
REFERENCES [dbo].[city] ([city_id])
GO
ALTER TABLE [dbo].[region] CHECK CONSTRAINT [FKedafr6k537xtdes7gc7nq778a]
GO
ALTER TABLE [dbo].[role]  WITH CHECK ADD  CONSTRAINT [FK780jtnokiyeo6ueyqtlm8yg96] FOREIGN KEY([access_master_id])
REFERENCES [dbo].[application_access] ([application_access_id])
GO
ALTER TABLE [dbo].[role] CHECK CONSTRAINT [FK780jtnokiyeo6ueyqtlm8yg96]
GO
ALTER TABLE [dbo].[state]  WITH CHECK ADD  CONSTRAINT [FKghic7mqjt6qb9vq7up7awu0er] FOREIGN KEY([country_id])
REFERENCES [dbo].[country] ([country_id])
GO
ALTER TABLE [dbo].[state] CHECK CONSTRAINT [FKghic7mqjt6qb9vq7up7awu0er]
GO
ALTER TABLE [dbo].[store]  WITH CHECK ADD  CONSTRAINT [FK5gf6vg5g9b0js56hmdhb9pcnn] FOREIGN KEY([city_id])
REFERENCES [dbo].[city] ([city_id])
GO
ALTER TABLE [dbo].[store] CHECK CONSTRAINT [FK5gf6vg5g9b0js56hmdhb9pcnn]
GO
ALTER TABLE [dbo].[store]  WITH CHECK ADD  CONSTRAINT [FK68x42pt4khc9bvu44y47e0n7j] FOREIGN KEY([country_id])
REFERENCES [dbo].[country] ([country_id])
GO
ALTER TABLE [dbo].[store] CHECK CONSTRAINT [FK68x42pt4khc9bvu44y47e0n7j]
GO
ALTER TABLE [dbo].[store]  WITH CHECK ADD  CONSTRAINT [FKbpcv7rci9dp0yg2hp9w02rfpi] FOREIGN KEY([web_master_id])
REFERENCES [dbo].[web_master] ([web_master_id])
GO
ALTER TABLE [dbo].[store] CHECK CONSTRAINT [FKbpcv7rci9dp0yg2hp9w02rfpi]
GO
ALTER TABLE [dbo].[store]  WITH CHECK ADD  CONSTRAINT [FKeudx0ek35nvl6gf8bwjlij879] FOREIGN KEY([store_service_type_id])
REFERENCES [dbo].[store_service_type] ([store_service_type_id])
GO
ALTER TABLE [dbo].[store] CHECK CONSTRAINT [FKeudx0ek35nvl6gf8bwjlij879]
GO
ALTER TABLE [dbo].[store]  WITH CHECK ADD  CONSTRAINT [FKiecbc1b9m21semcf714lasyi5] FOREIGN KEY([region_id])
REFERENCES [dbo].[region] ([region_id])
GO
ALTER TABLE [dbo].[store] CHECK CONSTRAINT [FKiecbc1b9m21semcf714lasyi5]
GO
ALTER TABLE [dbo].[store]  WITH CHECK ADD  CONSTRAINT [FKmico5ic0jpx9b67bdmijs2scl] FOREIGN KEY([store_business_service_type_id])
REFERENCES [dbo].[store_business_service_type] ([store_business_service_type_id])
GO
ALTER TABLE [dbo].[store] CHECK CONSTRAINT [FKmico5ic0jpx9b67bdmijs2scl]
GO
ALTER TABLE [dbo].[store]  WITH CHECK ADD  CONSTRAINT [FKo5puufyd6rv41mvejvgp3xdh] FOREIGN KEY([state_id])
REFERENCES [dbo].[state] ([state_id])
GO
ALTER TABLE [dbo].[store] CHECK CONSTRAINT [FKo5puufyd6rv41mvejvgp3xdh]
GO
ALTER TABLE [dbo].[store]  WITH CHECK ADD  CONSTRAINT [FKp5uq442mv80e99d3elba3bqnu] FOREIGN KEY([owner_type_id])
REFERENCES [dbo].[owner_type] ([owner_type_id])
GO
ALTER TABLE [dbo].[store] CHECK CONSTRAINT [FKp5uq442mv80e99d3elba3bqnu]
GO
ALTER TABLE [dbo].[store_alloted_details]  WITH CHECK ADD  CONSTRAINT [FK3j9e8u6aj5eab8c8yl2oe8frh] FOREIGN KEY([store_id])
REFERENCES [dbo].[store] ([store_id])
GO
ALTER TABLE [dbo].[store_alloted_details] CHECK CONSTRAINT [FK3j9e8u6aj5eab8c8yl2oe8frh]
GO
ALTER TABLE [dbo].[store_alloted_details]  WITH CHECK ADD  CONSTRAINT [FKk9sgtp1jbb0k5mhtynjgmkpn2] FOREIGN KEY([store_alloted_id])
REFERENCES [dbo].[store_alloted] ([store_alloted_id])
GO
ALTER TABLE [dbo].[store_alloted_details] CHECK CONSTRAINT [FKk9sgtp1jbb0k5mhtynjgmkpn2]
GO
ALTER TABLE [dbo].[sub_features]  WITH CHECK ADD  CONSTRAINT [FKatx7p1pvapw0pitxmuowdllo3] FOREIGN KEY([feature_id])
REFERENCES [dbo].[feature] ([feature_id])
GO
ALTER TABLE [dbo].[sub_features] CHECK CONSTRAINT [FKatx7p1pvapw0pitxmuowdllo3]
GO
ALTER TABLE [dbo].[sub_futures]  WITH CHECK ADD  CONSTRAINT [FK9ojatpkoo05ix6glblfoo799y] FOREIGN KEY([future_id])
REFERENCES [dbo].[futures] ([future_id])
GO
ALTER TABLE [dbo].[sub_futures] CHECK CONSTRAINT [FK9ojatpkoo05ix6glblfoo799y]
GO
ALTER TABLE [dbo].[sub_model]  WITH CHECK ADD  CONSTRAINT [FKdtmvg6da0peasf1i06gdgb1e9] FOREIGN KEY([model_id])
REFERENCES [dbo].[model] ([model_id])
GO
ALTER TABLE [dbo].[sub_model] CHECK CONSTRAINT [FKdtmvg6da0peasf1i06gdgb1e9]
GO
ALTER TABLE [dbo].[sub_module]  WITH CHECK ADD  CONSTRAINT [FKsiy8j1vihx4qrh9ap5mdfkn13] FOREIGN KEY([module_id])
REFERENCES [dbo].[module] ([model_id])
GO
ALTER TABLE [dbo].[sub_module] CHECK CONSTRAINT [FKsiy8j1vihx4qrh9ap5mdfkn13]
GO
ALTER TABLE [dbo].[sub_module]  WITH CHECK ADD  CONSTRAINT [FKte4h0yifarpj2hff3pkuiyx0x] FOREIGN KEY([model_id])
REFERENCES [dbo].[module] ([model_id])
GO
ALTER TABLE [dbo].[sub_module] CHECK CONSTRAINT [FKte4h0yifarpj2hff3pkuiyx0x]
GO
ALTER TABLE [dbo].[users]  WITH CHECK ADD  CONSTRAINT [FK7cpqyws7t8nmuwgpbsgc1ifxl] FOREIGN KEY([web_role_id])
REFERENCES [dbo].[web_role] ([web_role_id])
GO
ALTER TABLE [dbo].[users] CHECK CONSTRAINT [FK7cpqyws7t8nmuwgpbsgc1ifxl]
GO
ALTER TABLE [dbo].[vendor]  WITH CHECK ADD  CONSTRAINT [FK3jfcfyvxn7wjy3natop6mdtjh] FOREIGN KEY([city_id])
REFERENCES [dbo].[city] ([city_id])
GO
ALTER TABLE [dbo].[vendor] CHECK CONSTRAINT [FK3jfcfyvxn7wjy3natop6mdtjh]
GO
ALTER TABLE [dbo].[vendor]  WITH CHECK ADD  CONSTRAINT [FKikqqbcp36b35599hb9im4urei] FOREIGN KEY([web_master_id])
REFERENCES [dbo].[web_master] ([web_master_id])
GO
ALTER TABLE [dbo].[vendor] CHECK CONSTRAINT [FKikqqbcp36b35599hb9im4urei]
GO
ALTER TABLE [dbo].[vendor]  WITH CHECK ADD  CONSTRAINT [FKqfwmmjsu14yfllkajxh8r0nd0] FOREIGN KEY([vendor_type_id])
REFERENCES [dbo].[vendor_type] ([vendor_type_id])
GO
ALTER TABLE [dbo].[vendor] CHECK CONSTRAINT [FKqfwmmjsu14yfllkajxh8r0nd0]
GO
ALTER TABLE [dbo].[vendor_equipment]  WITH CHECK ADD  CONSTRAINT [FKif1hd3gi2p25gl57poikngmth] FOREIGN KEY([vendor_id])
REFERENCES [dbo].[vendor] ([vendor_id])
GO
ALTER TABLE [dbo].[vendor_equipment] CHECK CONSTRAINT [FKif1hd3gi2p25gl57poikngmth]
GO
ALTER TABLE [dbo].[web_role]  WITH CHECK ADD  CONSTRAINT [FKkibw6qe6ds4d9ux1250arjyrp] FOREIGN KEY([role_id])
REFERENCES [dbo].[role] ([role_id])
GO
ALTER TABLE [dbo].[web_role] CHECK CONSTRAINT [FKkibw6qe6ds4d9ux1250arjyrp]
GO
ALTER TABLE [dbo].[web_role]  WITH CHECK ADD  CONSTRAINT [FKricebnk16v7f5axyfur067hq7] FOREIGN KEY([access_master_id])
REFERENCES [dbo].[web_master] ([web_master_id])
GO
ALTER TABLE [dbo].[web_role] CHECK CONSTRAINT [FKricebnk16v7f5axyfur067hq7]
GO
ALTER TABLE [dbo].[web_role]  WITH CHECK ADD  CONSTRAINT [FKtjxt8sy0f8ypunwjcnfbr1yf0] FOREIGN KEY([reporting_id])
REFERENCES [dbo].[role] ([role_id])
GO
ALTER TABLE [dbo].[web_role] CHECK CONSTRAINT [FKtjxt8sy0f8ypunwjcnfbr1yf0]
GO
USE [master]
GO
ALTER DATABASE [Nxtwav] SET  READ_WRITE 
GO
